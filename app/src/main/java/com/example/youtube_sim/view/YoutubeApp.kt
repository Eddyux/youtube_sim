package com.example.youtube_sim.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.youtube_sim.model.RootTab
import com.example.youtube_sim.presenter.OverlayState
import com.example.youtube_sim.presenter.YoutubePresenterContract
import com.example.youtube_sim.view.component.BottomBar
import com.example.youtube_sim.view.component.ChannelScreen
import com.example.youtube_sim.view.component.GeneralScreen
import com.example.youtube_sim.view.component.HistoryScreen
import com.example.youtube_sim.view.component.HomeScreen
import com.example.youtube_sim.view.component.AboutScreen
import com.example.youtube_sim.view.component.LanguageSettingsScreen
import com.example.youtube_sim.view.component.NotificationInboxScreen
import com.example.youtube_sim.view.component.NotificationsScreen
import com.example.youtube_sim.view.component.PlaceholderScreen
import com.example.youtube_sim.view.component.PlaylistsOverviewScreen
import com.example.youtube_sim.view.component.QualitySettingsScreen
import com.example.youtube_sim.view.component.PlaylistScreen
import com.example.youtube_sim.view.component.SearchScreen
import com.example.youtube_sim.view.component.SettingsScreen
import com.example.youtube_sim.view.component.ShortsScreen
import com.example.youtube_sim.view.component.SubscriptionsScreen
import com.example.youtube_sim.view.component.VideoPlayScreen
import com.example.youtube_sim.view.component.YouScreen

@Composable
fun YoutubeApp(
    presenter: YoutubePresenterContract
) {
    val state = presenter.uiState
    val itemMap = state.homeTabs.flatMap { it.items }.associateBy { it.id }
    val shortsItems = state.homeTabs.firstOrNull { it.key == "shorts" }?.items.orEmpty()

    when (val overlay = state.overlay) {
        is OverlayState.Placeholder -> {
            PlaceholderScreen(
                title = overlay.title,
                description = overlay.description,
                onBack = presenter::dismissOverlay
            )
            return
        }

        OverlayState.Settings -> {
            SettingsScreen(
                groups = state.settingsGroups,
                onItemSelected = presenter::onSettingsItemSelected,
                onBack = presenter::dismissOverlay
            )
            return
        }

        OverlayState.General -> {
            GeneralScreen(
                items = state.generalSettings,
                toggleStates = state.toggleStates,
                onToggle = presenter::onToggle,
                onBack = { presenter.onSettingsItemSelected("__back_to_settings__") }
            )
            return
        }

        OverlayState.NotificationSettings -> {
            NotificationsScreen(
                items = state.notificationSettings,
                toggleStates = state.toggleStates,
                onToggle = presenter::onToggle,
                onBack = { presenter.onSettingsItemSelected("__back_to_settings__") }
            )
            return
        }

        OverlayState.NotificationInbox -> {
            NotificationInboxScreen(onBack = presenter::dismissOverlay)
            return
        }

        OverlayState.Languages -> {
            LanguageSettingsScreen(
                options = state.languageOptions,
                selectedKey = state.selectedOptions["app_language"].orEmpty(),
                onLanguageSelected = { presenter.onSelectionChanged("app_language", it) },
                onPreferredLanguagesClick = {
                    presenter.showPlaceholder(
                        "Preferred Languages",
                        "Preferred language ordering is kept as a placeholder for later work."
                    )
                },
                onLearnMoreClick = {
                    presenter.showPlaceholder(
                        "Learn more",
                        "This help entry is kept as a placeholder for later work."
                    )
                },
                onBack = { presenter.onSettingsItemSelected("__back_to_settings__") }
            )
            return
        }

        OverlayState.Quality -> {
            QualitySettingsScreen(
                sections = state.qualityPreferenceSections,
                selectedOptions = state.selectedOptions,
                onOptionSelected = presenter::onSelectionChanged,
                onBack = { presenter.onSettingsItemSelected("__back_to_settings__") }
            )
            return
        }

        OverlayState.About -> {
            AboutScreen(
                onBack = { presenter.onSettingsItemSelected("__back_to_settings__") }
            )
            return
        }

        OverlayState.History -> {
            HistoryScreen(
                sections = state.historySections,
                itemsById = itemMap,
                overflowActions = state.historyOverflowActions,
                onFeedItemSelected = presenter::onFeedItemSelected,
                onOverflowAction = presenter::onHistoryOverflowAction,
                onBack = presenter::dismissOverlay,
                onBottomTabSelected = presenter::onBottomTabSelected,
                onSearchRequested = { presenter.onHeaderActionSelected("search") }
            )
            return
        }

        OverlayState.Playlists -> {
            PlaylistsOverviewScreen(
                playlistPreviews = state.playlistPreviews,
                playlistDetails = state.playlistDetails,
                itemsById = itemMap,
                onPlaylistSelected = presenter::onYouEntrySelected,
                onBack = presenter::dismissOverlay,
                onCreatePlaylist = {
                    presenter.showPlaceholder(
                        "Create new playlist",
                        "Playlist creation is reserved as a placeholder entry."
                    )
                }
            )
            return
        }

        OverlayState.Search -> {
            SearchScreen(
                query = state.searchQuery,
                results = state.homeTabs.filter { it.key != "live" }.flatMap { it.items },
                onQueryChanged = presenter::onSearchQueryChanged,
                onFeedItemSelected = presenter::onFeedItemSelected,
                onBack = presenter::dismissOverlay
            )
            return
        }

        is OverlayState.Playlist -> {
            val playlist = state.playlistDetails.firstOrNull { it.key == overlay.key }
            if (playlist != null) {
                PlaylistScreen(
                    playlist = playlist,
                    itemsById = itemMap,
                    overflowActions = state.playlistOverflowActions,
                    onFeedItemSelected = presenter::onFeedItemSelected,
                    onOverflowAction = presenter::onPlaylistOverflowAction,
                    onBack = presenter::dismissOverlay,
                    onBottomTabSelected = presenter::onBottomTabSelected,
                    onSearchRequested = { presenter.onHeaderActionSelected("search") }
                )
            } else {
                PlaceholderScreen(
                    title = "In development",
                    description = "This playlist is not implemented yet.",
                    onBack = presenter::dismissOverlay
                )
            }
            return
        }

        is OverlayState.Channel -> {
            val profile = state.channelProfiles.firstOrNull { it.key == overlay.key }
            if (profile != null) {
                ChannelScreen(
                    profile = profile,
                    heroItem = itemMap[profile.heroItemId],
                    featuredItem = itemMap[profile.featuredItemId],
                    videos = profile.videoItemIds.mapNotNull(itemMap::get),
                    isSubscribed = overlay.key in state.subscribedChannels,
                    onSubscriptionToggle = { presenter.onChannelSubscriptionToggle(profile.key) },
                    onFeedItemSelected = presenter::onFeedItemSelected,
                    onBack = presenter::dismissOverlay
                )
            } else {
                PlaceholderScreen(
                    title = "Channel",
                    description = "This creator page is not available yet.",
                    onBack = presenter::dismissOverlay
                )
            }
            return
        }

        is OverlayState.VideoPlay -> {
            val standardRelatedItems = state.homeTabs
                .filter { it.key != "shorts" }
                .flatMap { it.items }
                .filter { it.id != overlay.item.id }
            val shortBonus = shortsItems.firstOrNull()
            val relatedItems = buildList {
                addAll(standardRelatedItems)
                if (shortBonus != null) {
                    add(shortBonus)
                }
            }
            val creatorProfileKey = state.channelProfiles.firstOrNull { profile ->
                profile.key == normalizeChannelKey(overlay.item.creator) ||
                    overlay.item.id.contains(profile.key.replace('_', '-'), ignoreCase = true)
            }?.key
            VideoPlayScreen(
                item = overlay.item,
                relatedItems = relatedItems,
                comments = state.commentsByVideoId[overlay.item.id] ?: state.comments,
                toggleStates = state.toggleStates,
                selectedOptions = state.selectedOptions,
                playSettingsItems = state.playSettingsItems,
                playSettingsMoreItems = state.playSettingsMoreItems,
                currentVideoQualityOptions = state.currentVideoQualityOptions,
                currentVideoResolutionLabel = state.currentVideoResolutionLabel,
                isVideoLiked = overlay.item.id in state.playlistDetails.firstOrNull { it.key == "liked_videos" }?.itemIds.orEmpty(),
                isVideoSaved = overlay.item.id in state.playlistDetails.firstOrNull { it.key == "watch_later" }?.itemIds.orEmpty(),
                isCreatorSubscribed = creatorProfileKey != null && creatorProfileKey in state.subscribedChannels,
                onToggle = presenter::onToggle,
                onSelectionChanged = presenter::onSelectionChanged,
                onFeedItemSelected = presenter::onFeedItemSelected,
                onVideoLikeToggle = presenter::onVideoLikeToggle,
                onVideoSaveToggle = presenter::onVideoSaveToggle,
                onCommentSubmitted = presenter::onCommentSubmitted,
                onChannelSelected = presenter::onChannelSelected,
                onSubscriptionToggle = {
                    if (creatorProfileKey != null) {
                        presenter.onChannelSubscriptionToggle(creatorProfileKey)
                    } else {
                        presenter.showPlaceholder(
                            "Subscribe",
                            "This creator page is not available yet."
                        )
                    }
                },
                onOpenGlobalQuality = { presenter.onSettingsItemSelected("Quality") },
                onPlaceholderRequested = presenter::showPlaceholder,
                onBack = presenter::dismissOverlay
            )
            return
        }

        null -> Unit
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(
                currentTab = state.currentRootTab,
                onTabSelected = presenter::onBottomTabSelected
            )
        }
    ) { innerPadding ->
        when (state.currentRootTab) {
            RootTab.HOME -> {
                HomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    homeTabs = state.homeTabs,
                    selectedChipKey = state.selectedHomeChipKey,
                    chips = state.homeChips,
                    actions = state.headerActions,
                    onChipSelected = presenter::onHomeChipSelected,
                    onActionSelected = presenter::onHeaderActionSelected,
                    onFeedItemSelected = presenter::onFeedItemSelected
                )
            }

            RootTab.SHORTS -> {
                ShortsScreen(
                    modifier = Modifier.padding(innerPadding),
                    items = shortsItems
                )
            }

            RootTab.SUBSCRIPTIONS -> {
                SubscriptionsScreen(
                    modifier = Modifier.padding(innerPadding),
                    groups = state.subscriptionGroups,
                    onChannelSelected = presenter::onChannelSelected
                )
            }

            RootTab.YOU -> {
                YouScreen(
                    modifier = Modifier.padding(innerPadding),
                    actions = state.headerActions,
                    historySections = state.historySections,
                    playlistPreviews = state.playlistPreviews,
                    playlistDetails = state.playlistDetails,
                    itemsById = itemMap,
                    entries = state.youEntries,
                    onActionSelected = presenter::onHeaderActionSelected,
                    historyOverflowActions = state.historyOverflowActions,
                    onEntrySelected = presenter::onYouEntrySelected,
                    onFeedItemSelected = presenter::onFeedItemSelected,
                    onHistoryOverflowAction = presenter::onHistoryOverflowAction
                )
            }
        }
    }
}

private fun normalizeChannelKey(value: String): String {
    val lowercase = value.lowercase()
    return when {
        "jay chou" in lowercase -> "jay_chou"
        else -> lowercase.replace(Regex("[^a-z0-9]+"), "_").trim('_')
    }
}
