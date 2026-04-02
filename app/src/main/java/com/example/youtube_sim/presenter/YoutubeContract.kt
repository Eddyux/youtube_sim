package com.example.youtube_sim.presenter

import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.GeneralSettingsItem
import com.example.youtube_sim.model.HeaderAction
import com.example.youtube_sim.model.HistoryPreview
import com.example.youtube_sim.model.HistorySection
import com.example.youtube_sim.model.HomeChip
import com.example.youtube_sim.model.HomeTabContent
import com.example.youtube_sim.model.NotificationSettingsItem
import com.example.youtube_sim.model.PlaySettingsMenuItem
import com.example.youtube_sim.model.PlaylistDetail
import com.example.youtube_sim.model.PlaylistPreview
import com.example.youtube_sim.model.RootTab
import com.example.youtube_sim.model.SettingsGroup
import com.example.youtube_sim.model.SubscriptionGroup
import com.example.youtube_sim.model.VideoComment
import com.example.youtube_sim.model.YouMenuEntry

sealed interface OverlayState {
    data class Placeholder(
        val title: String,
        val description: String
    ) : OverlayState

    data object Settings : OverlayState
    data object General : OverlayState
    data object Notifications : OverlayState
    data object History : OverlayState
    data class Playlist(val key: String) : OverlayState
    data class VideoPlay(val item: FeedItem) : OverlayState
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
    val historySections: List<HistorySection> = emptyList(),
    val playlistPreviews: List<PlaylistPreview> = emptyList(),
    val playlistDetails: List<PlaylistDetail> = emptyList(),
    val settingsGroups: List<SettingsGroup> = emptyList(),
    val generalSettings: List<GeneralSettingsItem> = emptyList(),
    val notificationSettings: List<NotificationSettingsItem> = emptyList(),
    val toggleStates: Map<String, Boolean> = emptyMap(),
    val playSettingsItems: List<PlaySettingsMenuItem> = emptyList(),
    val playSettingsMoreItems: List<PlaySettingsMenuItem> = emptyList(),
    val comments: List<VideoComment> = emptyList(),
    val overlay: OverlayState? = null
)

interface YoutubePresenterContract {
    val uiState: YoutubeUiState

    fun onBottomTabSelected(tab: RootTab)
    fun onHomeChipSelected(key: String)
    fun onHeaderActionSelected(key: String)
    fun onFeedItemSelected(itemId: String)
    fun onYouEntrySelected(key: String)
    fun onSettingsItemSelected(label: String)
    fun showPlaceholder(title: String, description: String)
    fun onToggle(key: String)
    fun dismissOverlay()
}
