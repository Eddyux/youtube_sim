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
import com.example.youtube_sim.view.component.HomeScreen
import com.example.youtube_sim.view.component.PlaceholderScreen
import com.example.youtube_sim.view.component.SectionLandingScreen
import com.example.youtube_sim.view.component.SettingsScreen
import com.example.youtube_sim.view.component.SubscriptionsScreen
import com.example.youtube_sim.view.component.YouScreen

@Composable
fun YoutubeApp(
    presenter: YoutubePresenterContract
) {
    val state = presenter.uiState
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
                    description = "Shorts 页面已预留入口，后续根据截图继续补齐。",
                    emoji = "🎬"
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
