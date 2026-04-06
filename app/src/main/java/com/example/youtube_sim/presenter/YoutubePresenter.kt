package com.example.youtube_sim.presenter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.youtube_sim.data.HomeFeedRepository
import com.example.youtube_sim.model.RootTab

class YoutubePresenter(
    repository: HomeFeedRepository
) : YoutubePresenterContract {

    private val initialCurrentVideoSelection = defaultSelections.getValue("quality_current_video")
    private val initialResolutionLabel = resolutionForCurrentVideo(initialCurrentVideoSelection)

    override var uiState by mutableStateOf(
        YoutubeUiState(
            homeTabs = repository.loadTabs(),
            homeChips = homeChips,
            headerActions = headerActions,
            youEntries = youEntries,
            subscriptionGroups = subscriptionGroups,
            historyPreviews = historyPreviews,
            historySections = historySections,
            playlistPreviews = playlistPreviews,
            playlistDetails = playlistDetails,
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

    override fun onBottomTabSelected(tab: RootTab) {
        uiState = uiState.copy(currentRootTab = tab, overlay = null)
    }

    override fun onHomeChipSelected(key: String) {
        val chip = homeChips.firstOrNull { it.key == key } ?: return
        if (chip.implemented) {
            uiState = uiState.copy(selectedHomeChipKey = key, currentRootTab = RootTab.HOME, overlay = null)
        } else {
            showPlaceholder(chip.label, "This entry is reserved for later implementation.")
        }
    }

    override fun onHeaderActionSelected(key: String) {
        val action = headerActions.firstOrNull { it.key == key } ?: return
        when (key) {
            "notifications" -> uiState = uiState.copy(overlay = OverlayState.Notifications)
            else -> showPlaceholder(action.label, "${action.label} is kept as an entry point for later work.")
        }
    }

    override fun onFeedItemSelected(itemId: String) {
        val item = uiState.homeTabs.flatMap { it.items }.firstOrNull { it.id == itemId }
        if (item != null) {
            uiState = uiState.copy(overlay = OverlayState.VideoPlay(item = item))
        } else {
            showPlaceholder("In development", "The destination for '$itemId' is not implemented yet.")
        }
    }

    override fun onYouEntrySelected(key: String) {
        when (key) {
            "history" -> uiState = uiState.copy(overlay = OverlayState.History)
            "settings" -> uiState = uiState.copy(overlay = OverlayState.Settings)
            "watch_later", "liked_videos" -> uiState = uiState.copy(overlay = OverlayState.Playlist(key))
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
            "__back_to_settings__" -> uiState = uiState.copy(overlay = OverlayState.Settings)
            "General" -> uiState = uiState.copy(overlay = OverlayState.General)
            "Notifications" -> uiState = uiState.copy(overlay = OverlayState.Notifications)
            "Languages" -> uiState = uiState.copy(overlay = OverlayState.Languages)
            "Quality" -> uiState = uiState.copy(overlay = OverlayState.Quality)
            else -> showPlaceholder(label, "$label is kept as a placeholder entry.")
        }
    }

    override fun showPlaceholder(title: String, description: String) {
        uiState = uiState.copy(overlay = OverlayState.Placeholder(title = title, description = description))
    }

    override fun onToggle(key: String) {
        val current = uiState.toggleStates[key] ?: false
        uiState = uiState.copy(toggleStates = uiState.toggleStates + (key to !current))
    }

    override fun onSelectionChanged(groupKey: String, optionKey: String) {
        val updatedSelections = uiState.selectedOptions + (groupKey to optionKey)
        if (groupKey == "quality_current_video") {
            val resolutionLabel = resolutionForCurrentVideo(optionKey)
            uiState = uiState.copy(
                selectedOptions = updatedSelections,
                playSettingsItems = buildPlaySettingsItems(optionKey, resolutionLabel),
                currentVideoResolutionLabel = resolutionLabel
            )
        } else {
            uiState = uiState.copy(selectedOptions = updatedSelections)
        }
    }

    override fun dismissOverlay() {
        uiState = uiState.copy(overlay = null)
    }
}
