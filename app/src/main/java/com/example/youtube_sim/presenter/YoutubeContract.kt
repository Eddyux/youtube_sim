package com.example.youtube_sim.presenter

import com.example.youtube_sim.model.HeaderAction
import com.example.youtube_sim.model.HistoryPreview
import com.example.youtube_sim.model.HomeChip
import com.example.youtube_sim.model.HomeTabContent
import com.example.youtube_sim.model.PlaylistPreview
import com.example.youtube_sim.model.RootTab
import com.example.youtube_sim.model.SettingsGroup
import com.example.youtube_sim.model.SubscriptionGroup
import com.example.youtube_sim.model.YouMenuEntry

sealed interface OverlayState {
    data class Placeholder(
        val title: String,
        val description: String
    ) : OverlayState

    data object Settings : OverlayState
}

data class YoutubeUiState(
    val currentRootTab: RootTab = RootTab.HOME,
    val selectedHomeChipKey: String = "all",
    val homeTabs: List<HomeTabContent> = emptyList(),
    val homeChips: List<HomeChip> = emptyList(),
    val headerActions: List<HeaderAction> = emptyList(),
    val youEntries: List<YouMenuEntry> = emptyList(),
    val subscriptionGroups: List<SubscriptionGroup> = emptyList(),
    val historyPreviews: List<HistoryPreview> = emptyList(),
    val playlistPreviews: List<PlaylistPreview> = emptyList(),
    val settingsGroups: List<SettingsGroup> = emptyList(),
    val overlay: OverlayState? = null
)

interface YoutubePresenterContract {
    val uiState: YoutubeUiState

    fun onBottomTabSelected(tab: RootTab)
    fun onHomeChipSelected(key: String)
    fun onHeaderActionSelected(key: String)
    fun onFeedItemSelected(title: String)
    fun onYouEntrySelected(key: String)
    fun dismissOverlay()
}
