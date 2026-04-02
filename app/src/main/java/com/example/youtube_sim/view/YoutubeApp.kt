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
import com.example.youtube_sim.view.component.GeneralScreen
import com.example.youtube_sim.view.component.HistoryScreen
import com.example.youtube_sim.view.component.HomeScreen
import com.example.youtube_sim.view.component.NotificationsScreen
import com.example.youtube_sim.view.component.PlaceholderScreen
import com.example.youtube_sim.view.component.PlaylistScreen
import com.example.youtube_sim.view.component.SectionLandingScreen
import com.example.youtube_sim.view.component.SettingsScreen
import com.example.youtube_sim.view.component.SubscriptionsScreen
import com.example.youtube_sim.view.component.VideoPlayScreen
import com.example.youtube_sim.view.component.YouScreen

@Composable
fun YoutubeApp(
    presenter: YoutubePresenterContract
) {
    val state = presenter.uiState
    val itemMap = state.homeTabs.flatMap { it.items }.associateBy { it.id }

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

        OverlayState.Notifications -> {
            NotificationsScreen(
                items = state.notificationSettings,
                toggleStates = state.toggleStates,
                onToggle = presenter::onToggle,
                onBack = { presenter.onSettingsItemSelected("__back_to_settings__") }
            )
            return
        }

        OverlayState.History -> {
            HistoryScreen(
                sections = state.historySections,
                itemsById = itemMap,
                onFeedItemSelected = presenter::onFeedItemSelected,
                onBack = presenter::dismissOverlay,
                onBottomTabSelected = presenter::onBottomTabSelected,
                onPlaceholderRequested = presenter::showPlaceholder
            )
            return
        }

        is OverlayState.Playlist -> {
            val playlist = state.playlistDetails.firstOrNull { it.key == overlay.key }
            if (playlist != null) {
                PlaylistScreen(
                    playlist = playlist,
                    itemsById = itemMap,
                    onFeedItemSelected = presenter::onFeedItemSelected,
                    onBack = presenter::dismissOverlay,
                    onBottomTabSelected = presenter::onBottomTabSelected,
                    onPlaceholderRequested = presenter::showPlaceholder
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

        is OverlayState.VideoPlay -> {
            val relatedItems = state.homeTabs.flatMap { it.items }.filter { it.id != overlay.item.id }
            VideoPlayScreen(
                item = overlay.item,
                relatedItems = relatedItems,
                comments = state.comments,
                toggleStates = state.toggleStates,
                playSettingsItems = state.playSettingsItems,
                playSettingsMoreItems = state.playSettingsMoreItems,
                onToggle = presenter::onToggle,
                onFeedItemSelected = presenter::onFeedItemSelected,
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
                SectionLandingScreen(
                    modifier = Modifier.padding(innerPadding),
                    title = "Shorts",
                    description = "Shorts is reserved as a placeholder entry for later work.",
                    emoji = "🎞"
                )
            }

            RootTab.SUBSCRIPTIONS -> {
                SubscriptionsScreen(
                    modifier = Modifier.padding(innerPadding),
                    groups = state.subscriptionGroups,
                    onChannelSelected = presenter::onFeedItemSelected
                )
            }

            RootTab.YOU -> {
                YouScreen(
                    modifier = Modifier.padding(innerPadding),
                    actions = state.headerActions,
                    historyPreviews = state.historyPreviews,
                    playlistPreviews = state.playlistPreviews,
                    entries = state.youEntries,
                    onActionSelected = presenter::onHeaderActionSelected,
                    onEntrySelected = presenter::onYouEntrySelected
                )
            }
        }
    }
}
