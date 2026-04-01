package com.example.youtube_sim.presenter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.youtube_sim.data.HomeFeedRepository
import com.example.youtube_sim.model.HeaderAction
import com.example.youtube_sim.model.HistoryPreview
import com.example.youtube_sim.model.HomeChip
import com.example.youtube_sim.model.PlaylistPreview
import com.example.youtube_sim.model.RootTab
import com.example.youtube_sim.model.SettingsGroup
import com.example.youtube_sim.model.SettingsItem
import com.example.youtube_sim.model.SubscriptionChannel
import com.example.youtube_sim.model.SubscriptionGroup
import com.example.youtube_sim.model.YouMenuEntry

class YoutubePresenter(
    repository: HomeFeedRepository
) : YoutubePresenterContract {

    override var uiState by mutableStateOf(
        YoutubeUiState(
            homeTabs = repository.loadTabs(),
            homeChips = homeChips,
            headerActions = headerActions,
            youEntries = youEntries,
            subscriptionGroups = subscriptionGroups,
            historyPreviews = historyPreviews,
            playlistPreviews = playlistPreviews,
            settingsGroups = settingsGroups
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
            showPlaceholder(chip.label, "该分类入口已保留，后续再继续开发。")
        }
    }

    override fun onHeaderActionSelected(key: String) {
        val action = headerActions.firstOrNull { it.key == key } ?: return
        showPlaceholder(action.label, "${action.label} 功能暂未实现，当前只保留入口。")
    }

    override fun onFeedItemSelected(title: String) {
        showPlaceholder("播放页开发中", "“$title” 当前只完成列表展示，播放页后续接入。")
    }

    override fun onYouEntrySelected(key: String) {
        val entry = youEntries.firstOrNull { it.key == key } ?: return
        if (entry.key == "settings") {
            uiState = uiState.copy(overlay = OverlayState.Settings)
        } else {
            showPlaceholder(entry.label, "${entry.label} 页面暂未实现，当前保留结构入口。")
        }
    }

    override fun dismissOverlay() {
        uiState = uiState.copy(overlay = null)
    }

    private fun showPlaceholder(title: String, description: String) {
        uiState = uiState.copy(overlay = OverlayState.Placeholder(title = title, description = description))
    }

    private companion object {
        val homeChips = listOf(
            HomeChip(key = "all", label = "All", implemented = true),
            HomeChip(key = "podcasts", label = "Podcasts", implemented = false),
            HomeChip(key = "music", label = "Music", implemented = true),
            HomeChip(key = "apple", label = "Apple", implemented = true),
            HomeChip(key = "live", label = "Live", implemented = true)
        )

        val headerActions = listOf(
            HeaderAction(key = "cast", emoji = "📺", label = "Cast"),
            HeaderAction(key = "notifications", emoji = "🔔", label = "Notifications"),
            HeaderAction(key = "search", emoji = "🔍", label = "Search")
        )

        val youEntries = listOf(
            YouMenuEntry(key = "history", label = "History", subtitle = "最近观看与记录", emoji = "🕘"),
            YouMenuEntry(key = "playlists", label = "Playlists", subtitle = "收藏与稍后观看", emoji = "📁"),
            YouMenuEntry(key = "settings", label = "Settings", subtitle = "界面与帐号设置入口", emoji = "⚙️"),
            YouMenuEntry(key = "your-videos", label = "Your videos", subtitle = "上传与创作入口", emoji = "🎞️")
        )

        val subscriptionGroups = listOf(
            SubscriptionGroup(
                title = "Beauty & Fashion",
                channels = listOf(
                    SubscriptionChannel("Jeffreestar", true),
                    SubscriptionChannel("Courtney W", false),
                    SubscriptionChannel("Miaaaw Love", false)
                )
            ),
            SubscriptionGroup(
                title = "Comedy",
                channels = listOf(
                    SubscriptionChannel("Dropout", true),
                    SubscriptionChannel("A.I. Games", false),
                    SubscriptionChannel("Alan Chikin", true)
                )
            ),
            SubscriptionGroup(
                title = "Music",
                channels = listOf(
                    SubscriptionChannel("M2M", false),
                    SubscriptionChannel("Neko Music", false),
                    SubscriptionChannel("Post Malone", true)
                )
            ),
            SubscriptionGroup(
                title = "Tech",
                channels = listOf(
                    SubscriptionChannel("MKBHD", true),
                    SubscriptionChannel("Tech YES City", true),
                    SubscriptionChannel("Hardware Canucks", true)
                )
            )
        )

        val historyPreviews = listOf(
            HistoryPreview("迈疯所有世界冠军的“玻璃友”", "#93C5FD", "#374151"),
            HistoryPreview("你真以为我不会啊！#shorts", "#BFDBFE", "#60A5FA"),
            HistoryPreview("Huawei GT 5 Pro", "#D6BCFA", "#7C3AED")
        )

        val playlistPreviews = listOf(
            PlaylistPreview("Watch later", "Private", "0"),
            PlaylistPreview("Liked videos", "Private", "0")
        )

        val settingsGroups = listOf(
            SettingsGroup(
                title = "Account",
                items = listOf(
                    SettingsItem("General", "⚙️"),
                    SettingsItem("Switch or manage account", "👤"),
                    SettingsItem("Family Center", "👨‍👩‍👧"),
                    SettingsItem("Languages", "🌐"),
                    SettingsItem("Time management", "⏳"),
                    SettingsItem("Notifications", "🔔"),
                    SettingsItem("Purchases and memberships", "🏷️"),
                    SettingsItem("Billing & payments", "💳"),
                    SettingsItem("Manage all history", "🕘"),
                    SettingsItem("Your data in YouTube", "📍"),
                    SettingsItem("Privacy", "🔒"),
                    SettingsItem("Connected apps", "🔗"),
                    SettingsItem("Try experimental new features", "🧪")
                )
            ),
            SettingsGroup(
                title = "Video and audio preferences",
                items = listOf(
                    SettingsItem("Quality", "▶️"),
                    SettingsItem("Playback", "⏯️"),
                    SettingsItem("Captions", "💬"),
                    SettingsItem("Data saving", "📶"),
                    SettingsItem("Accessibility", "♿"),
                    SettingsItem("Watch on TV", "📺")
                )
            ),
            SettingsGroup(
                title = "Help and policy",
                items = listOf(
                    SettingsItem("Help", "❓"),
                    SettingsItem("YouTube Terms of Service", "📄"),
                    SettingsItem("Send feedback", "💭"),
                    SettingsItem("About", "ℹ️")
                )
            )
        )
    }
}
