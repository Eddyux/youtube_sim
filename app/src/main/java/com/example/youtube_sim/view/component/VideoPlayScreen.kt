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
import com.example.youtube_sim.model.QualityOption
import com.example.youtube_sim.model.VideoComment

@Composable
fun VideoPlayScreen(
    item: FeedItem,
    relatedItems: List<FeedItem>,
    comments: List<VideoComment>,
    toggleStates: Map<String, Boolean>,
    selectedOptions: Map<String, String>,
    playSettingsItems: List<PlaySettingsMenuItem>,
    playSettingsMoreItems: List<PlaySettingsMenuItem>,
    currentVideoQualityOptions: List<QualityOption>,
    currentVideoResolutionLabel: String,
    onToggle: (String) -> Unit,
    onSelectionChanged: (String, String) -> Unit,
    onFeedItemSelected: (String) -> Unit,
    onOpenGlobalQuality: () -> Unit,
    onPlaceholderRequested: (String, String) -> Unit,
    onBack: () -> Unit
) {
    var showSettings by remember { mutableStateOf(false) }
    var showSettingsMore by remember { mutableStateOf(false) }
    var showQuality by remember { mutableStateOf(false) }
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
                onItemSelected = { key ->
                    when (key) {
                        "quality" -> {
                            showSettings = false
                            showQuality = true
                        }

                        else -> showSettings = false
                    }
                },
                onMoreClick = {
                    showSettings = false
                    showSettingsMore = true
                }
            )
        }

        if (showQuality) {
            PlayCurrentVideoQualitySheet(
                options = currentVideoQualityOptions,
                selectedKey = selectedOptions["quality_current_video"].orEmpty(),
                currentResolutionLabel = currentVideoResolutionLabel,
                onOptionSelected = { optionKey ->
                    showQuality = false
                    if (optionKey == "advanced") {
                        onPlaceholderRequested(
                            "Advanced quality",
                            "Specific resolution selection is kept as a placeholder for later work."
                        )
                    } else {
                        onSelectionChanged("quality_current_video", optionKey)
                    }
                },
                onGlobalQualityClick = {
                    showQuality = false
                    onOpenGlobalQuality()
                },
                onDismiss = { showQuality = false }
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
