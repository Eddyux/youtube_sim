package com.example.youtube_sim.view.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.PlaySettingsMenuItem
import com.example.youtube_sim.model.VideoComment

@Composable
fun VideoPlayScreen(
    item: FeedItem,
    relatedItems: List<FeedItem>,
    comments: List<VideoComment>,
    toggleStates: Map<String, Boolean>,
    playSettingsItems: List<PlaySettingsMenuItem>,
    playSettingsMoreItems: List<PlaySettingsMenuItem>,
    onToggle: (String) -> Unit,
    onFeedItemSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    var showSettings by remember { mutableStateOf(false) }
    var showSettingsMore by remember { mutableStateOf(false) }
    var showComments by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        VideoPlayContent(
            item = item,
            relatedItems = relatedItems,
            comments = comments,
            onFeedItemSelected = onFeedItemSelected,
            onBack = onBack,
            onSettingsClick = { showSettings = true },
            onCommentsClick = { showComments = true }
        )

        if (showSettings) {
            PlaySettingsSheet(
                items = playSettingsItems,
                onDismiss = { showSettings = false },
                onMoreClick = {
                    showSettings = false
                    showSettingsMore = true
                }
            )
        }

        if (showSettingsMore) {
            PlaySettingsMoreSheet(
                items = playSettingsMoreItems,
                toggleStates = toggleStates,
                onToggle = onToggle,
                onDismiss = { showSettingsMore = false }
            )
        }

        if (showComments) {
            CommentsSheet(comments = comments, onDismiss = { showComments = false })
        }
    }
}
