package com.example.youtube_sim.presenter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.youtube_sim.data.HomeFeedRepository
import com.example.youtube_sim.data.TaskStateStore
import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.InteractionEvent
import com.example.youtube_sim.model.InteractionSnapshot
import com.example.youtube_sim.model.RootTab
import com.example.youtube_sim.model.VideoComment

class YoutubePresenter(
    repository: HomeFeedRepository,
    private val taskStateStore: TaskStateStore
) : YoutubePresenterContract {
    private val initialTabs = repository.loadTabs()
    private val initialPlaylistDetails = refreshPlaylistDetails(playlistDetails)
    private val initialCurrentVideoSelection = defaultSelections.getValue("quality_current_video")
    private val initialResolutionLabel = resolutionForCurrentVideo(initialCurrentVideoSelection)
    private val events = mutableListOf<InteractionEvent>()
    private val postedComments = linkedMapOf<String, MutableList<String>>()
    private var lastPlayedVideoId: String? = null

    override var uiState by mutableStateOf(
        YoutubeUiState(
            homeTabs = initialTabs,
            homeChips = homeChips,
            headerActions = headerActions,
            youEntries = youEntries,
            subscriptionGroups = subscriptionGroups,
            historyPreviews = historyPreviews,
            historySections = historySections,
            playlistPreviews = buildPlaylistPreviews(initialPlaylistDetails),
            playlistDetails = initialPlaylistDetails,
            historyOverflowActions = historyOverflowActions,
            playlistOverflowActions = playlistOverflowActions,
            channelProfiles = buildChannelProfiles(initialTabs),
            settingsGroups = settingsGroups,
            generalSettings = generalSettings,
            notificationSettings = notificationSettings,
            languageOptions = languageOptions,
            qualityPreferenceSections = qualityPreferenceSections,
            currentVideoQualityOptions = currentVideoQualityOptions,
            toggleStates = defaultToggles,
            selectedOptions = defaultSelections,
            playSettingsItems = buildPlaySettingsItems(initialCurrentVideoSelection, initialResolutionLabel),
            playSettingsMoreItems = playSettingsMoreItems,
            comments = comments,
            currentVideoResolutionLabel = initialResolutionLabel
        )
    )
        private set

    init {
        persistState(reset = true)
    }

    override fun onBottomTabSelected(tab: RootTab) {
        uiState = uiState.copy(currentRootTab = tab, overlay = null)
        persistState()
    }

    override fun onHomeChipSelected(key: String) {
        val chip = homeChips.firstOrNull { it.key == key } ?: return
        if (chip.implemented) {
            uiState = uiState.copy(selectedHomeChipKey = key, currentRootTab = RootTab.HOME, overlay = null)
        } else {
            showPlaceholder(chip.label, "This entry is reserved for later implementation.")
            return
        }
        persistState()
    }

    override fun onHeaderActionSelected(key: String) {
        val action = headerActions.firstOrNull { it.key == key } ?: return
        when (key) {
            "notifications" -> {
                uiState = uiState.copy(overlay = OverlayState.NotificationInbox)
                recordEvent("open_overlay", "notification_inbox")
            }
            "search" -> {
                uiState = uiState.copy(overlay = OverlayState.Search)
                recordEvent("open_overlay", "search")
            }
            else -> showPlaceholder(action.label, "${action.label} is kept as an entry point for later work.")
        }
    }

    override fun onFeedItemSelected(itemId: String) {
        val item = uiState.homeTabs.flatMap { it.items }.firstOrNull { it.id == itemId }
        if (item != null) {
            lastPlayedVideoId = itemId
            uiState = uiState.copy(overlay = OverlayState.VideoPlay(item = item))
            recordEvent("play_video", itemId)
        } else {
            showPlaceholder("In development", "The destination for '$itemId' is not implemented yet.")
        }
    }

    override fun onChannelSelected(key: String) {
        val channelKey = normalizeChannelKey(key)
        val profile = uiState.channelProfiles.firstOrNull { it.key == channelKey }
        if (profile != null) {
            uiState = uiState.copy(overlay = OverlayState.Channel(profile.key))
            recordEvent("open_overlay", "channel:$channelKey")
        } else {
            showPlaceholder("Channel", "This creator page is reserved for later work.")
        }
    }

    override fun onChannelSubscriptionToggle(key: String) {
        val subscriptions = uiState.subscribedChannels.toMutableSet()
        val subscribed = subscriptions.add(key)
        if (!subscribed) {
            subscriptions.remove(key)
        }
        uiState = uiState.copy(subscribedChannels = subscriptions)
        recordEvent("toggle_channel_subscription", key, mapOf("enabled" to subscribed.toString()))
    }

    override fun onHistoryOverflowAction(itemId: String, actionKey: String) {
        when (actionKey) {
            "watch_later" -> addItemToPlaylist("watch_later", itemId, "save_from_history")
            "remove_history" -> {
                val updatedSections = uiState.historySections.mapNotNull { section ->
                    val remaining = section.entries.filterNot { it.itemId == itemId }
                    if (remaining.isEmpty()) null else section.copy(entries = remaining)
                }
                uiState = uiState.copy(historySections = updatedSections)
                recordEvent("remove_history_item", itemId)
            }
        }
    }

    override fun onPlaylistOverflowAction(playlistKey: String, itemId: String, actionKey: String) {
        when (actionKey) {
            "watch_later" -> addItemToPlaylist("watch_later", itemId, "save_from_playlist")
            "remove_playlist" -> removeItemFromPlaylist(playlistKey, itemId)
        }
    }

    override fun onYouEntrySelected(key: String) {
        when (key) {
            "history" -> openOverlay(OverlayState.History, "history")
            "playlists" -> openOverlay(OverlayState.Playlists, "playlists")
            "settings" -> openOverlay(OverlayState.Settings, "settings")
            "watch_later", "liked_videos" -> openOverlay(OverlayState.Playlist(key), "playlist:$key")
            else -> {
                val entry = youEntries.firstOrNull { it.key == key }
                val title = entry?.label ?: "In development"
                val description = if (entry != null) {
                    "${entry.label} is kept as a placeholder entry."
                } else {
                    "This destination is not implemented yet."
                }
                showPlaceholder(title, description)
            }
        }
    }

    override fun onSettingsItemSelected(label: String) {
        when (label) {
            "__back_to_settings__" -> openOverlay(OverlayState.Settings, "settings")
            "General" -> openOverlay(OverlayState.General, "general_settings")
            "Notifications" -> openOverlay(OverlayState.NotificationSettings, "notification_settings")
            "Languages" -> openOverlay(OverlayState.Languages, "language_settings")
            "Quality" -> openOverlay(OverlayState.Quality, "quality_settings")
            else -> showPlaceholder(label, "$label is kept as a placeholder entry.")
        }
    }

    override fun onSearchQueryChanged(query: String) {
        uiState = uiState.copy(searchQuery = query)
        val normalized = query.trim()
        val details = if (normalized.isBlank()) emptyMap() else mapOf("query" to normalized)
        recordEvent("search_query_changed", normalized.ifBlank { "blank" }, details)
    }

    override fun showPlaceholder(title: String, description: String) {
        uiState = uiState.copy(overlay = OverlayState.Placeholder(title = title, description = description))
        persistState()
    }

    override fun onToggle(key: String) {
        val current = uiState.toggleStates[key] ?: false
        val updated = !current
        uiState = uiState.copy(toggleStates = uiState.toggleStates + (key to updated))
        recordEvent("toggle_setting", key, mapOf("enabled" to updated.toString()))
    }

    override fun onSelectionChanged(groupKey: String, optionKey: String) {
        val updatedSelections = uiState.selectedOptions + (groupKey to optionKey)
        uiState = if (groupKey == "quality_current_video") {
            val resolutionLabel = resolutionForCurrentVideo(optionKey)
            uiState.copy(
                selectedOptions = updatedSelections,
                playSettingsItems = buildPlaySettingsItems(optionKey, resolutionLabel),
                currentVideoResolutionLabel = resolutionLabel
            )
        } else {
            uiState.copy(selectedOptions = updatedSelections)
        }
        persistState()
    }

    override fun onVideoLikeToggle(itemId: String) {
        val enabled = togglePlaylistMembership("liked_videos", itemId)
        recordEvent("toggle_video_like", itemId, mapOf("enabled" to enabled.toString()))
    }

    override fun onVideoSaveToggle(itemId: String) {
        val enabled = togglePlaylistMembership("watch_later", itemId)
        recordEvent("toggle_video_save", itemId, mapOf("enabled" to enabled.toString()))
    }

    override fun onCommentSubmitted(itemId: String, text: String) {
        val trimmed = text.trim()
        if (trimmed.isBlank()) return
        val currentComments = commentsFor(itemId)
        val newComment = VideoComment(
            author = "Eddy",
            handle = "@eddy",
            text = trimmed,
            likes = "0",
            timeAgo = "Just now"
        )
        uiState = uiState.copy(commentsByVideoId = uiState.commentsByVideoId + (itemId to (listOf(newComment) + currentComments)))
        postedComments.getOrPut(itemId) { mutableListOf() }.add(trimmed)
        recordEvent("submit_comment", itemId, mapOf("text" to trimmed))
    }

    override fun dismissOverlay() {
        val fallbackOverlay = when (uiState.overlay) {
            OverlayState.General,
            OverlayState.NotificationSettings,
            OverlayState.Languages,
            OverlayState.Quality -> OverlayState.Settings
            else -> null
        }
        uiState = uiState.copy(overlay = fallbackOverlay)
        persistState()
    }

    private fun openOverlay(overlay: OverlayState, target: String) {
        uiState = uiState.copy(overlay = overlay)
        recordEvent("open_overlay", target)
    }

    private fun commentsFor(itemId: String): List<VideoComment> {
        return uiState.commentsByVideoId[itemId] ?: uiState.comments
    }

    private fun normalizeChannelKey(value: String): String {
        val lowercase = value.lowercase()
        return when {
            "jay chou" in lowercase -> "jay_chou"
            else -> lowercase.replace(Regex("[^a-z0-9]+"), "_").trim('_')
        }
    }

    private fun addItemToPlaylist(playlistKey: String, itemId: String, action: String) {
        val detail = uiState.playlistDetails.firstOrNull { it.key == playlistKey } ?: return
        if (itemId in detail.itemIds) {
            return
        }
        val updated = uiState.playlistDetails.map { current ->
            if (current.key == playlistKey) {
                current.copy(itemIds = listOf(itemId) + current.itemIds)
            } else {
                current
            }
        }
        applyPlaylistDetails(updated)
        recordEvent(action, itemId, mapOf("playlist" to playlistKey))
    }

    private fun removeItemFromPlaylist(playlistKey: String, itemId: String) {
        val updated = uiState.playlistDetails.map { detail ->
            if (detail.key == playlistKey) {
                detail.copy(itemIds = detail.itemIds.filterNot { it == itemId })
            } else {
                detail
            }
        }
        applyPlaylistDetails(updated)
        recordEvent("remove_playlist_item", itemId, mapOf("playlist" to playlistKey))
    }

    private fun togglePlaylistMembership(playlistKey: String, itemId: String): Boolean {
        val playlist = uiState.playlistDetails.firstOrNull { it.key == playlistKey } ?: return false
        val enabled = itemId !in playlist.itemIds
        val updated = uiState.playlistDetails.map { detail ->
            if (detail.key == playlistKey) {
                val itemIds = if (enabled) listOf(itemId) + detail.itemIds else detail.itemIds.filterNot { it == itemId }
                detail.copy(itemIds = itemIds)
            } else {
                detail
            }
        }
        applyPlaylistDetails(updated)
        return enabled
    }

    private fun applyPlaylistDetails(details: List<com.example.youtube_sim.model.PlaylistDetail>) {
        val refreshed = refreshPlaylistDetails(details)
        uiState = uiState.copy(
            playlistDetails = refreshed,
            playlistPreviews = buildPlaylistPreviews(refreshed)
        )
    }

    private fun recordEvent(action: String, target: String? = null, details: Map<String, String> = emptyMap()) {
        events += InteractionEvent(action = action, target = target, details = details)
        persistState()
    }

    private fun persistState(reset: Boolean = false) {
        val snapshot = InteractionSnapshot(
            currentRootTab = uiState.currentRootTab.name.lowercase(),
            selectedHomeChipKey = uiState.selectedHomeChipKey,
            activeOverlay = overlayKey(uiState.overlay),
            searchQuery = uiState.searchQuery.trim(),
            toggleStates = uiState.toggleStates,
            selectedOptions = uiState.selectedOptions,
            subscribedChannels = uiState.subscribedChannels,
            likedVideoIds = playlistItemsFor("liked_videos"),
            savedVideoIds = playlistItemsFor("watch_later"),
            playlistItems = uiState.playlistDetails.associate { it.key to it.itemIds },
            postedComments = postedComments.mapValues { entry -> entry.value.toList() },
            lastPlayedVideoId = lastPlayedVideoId,
            events = events.toList()
        )
        if (reset) {
            taskStateStore.reset(snapshot)
        } else {
            taskStateStore.save(snapshot)
        }
    }

    private fun playlistItemsFor(key: String): List<String> {
        return uiState.playlistDetails.firstOrNull { it.key == key }?.itemIds.orEmpty()
    }

    private fun overlayKey(overlay: OverlayState?): String? {
        return when (overlay) {
            null -> null
            is OverlayState.Placeholder -> "placeholder"
            OverlayState.Settings -> "settings"
            OverlayState.General -> "general"
            OverlayState.NotificationSettings -> "notification_settings"
            OverlayState.NotificationInbox -> "notification_inbox"
            OverlayState.Languages -> "languages"
            OverlayState.Quality -> "quality"
            OverlayState.History -> "history"
            OverlayState.Playlists -> "playlists"
            OverlayState.Search -> "search"
            is OverlayState.Playlist -> "playlist:${overlay.key}"
            is OverlayState.Channel -> "channel:${overlay.key}"
            is OverlayState.VideoPlay -> "video_play:${overlay.item.id}"
        }
    }
}
