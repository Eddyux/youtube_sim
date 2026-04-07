package com.example.youtube_sim.view.component
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.HistorySection
import com.example.youtube_sim.model.OverflowMenuAction
import com.example.youtube_sim.model.PlaylistDetail
import com.example.youtube_sim.model.PlaylistPreview
import com.example.youtube_sim.model.YouMenuEntry

@Composable
fun YouScreen(
    modifier: Modifier = Modifier,
    actions: List<com.example.youtube_sim.model.HeaderAction>,
    historySections: List<HistorySection>,
    historyOverflowActions: List<OverflowMenuAction>,
    playlistPreviews: List<PlaylistPreview>,
    playlistDetails: List<PlaylistDetail>,
    itemsById: Map<String, FeedItem>,
    entries: List<YouMenuEntry>,
    onActionSelected: (String) -> Unit,
    onEntrySelected: (String) -> Unit,
    onFeedItemSelected: (String) -> Unit,
    onHistoryOverflowAction: (String, String) -> Unit
) {
    var historyMenuItemId by remember { mutableStateOf<String?>(null) }
    val historyPreviewItems = historySections.flatMap { it.entries }.mapNotNull { itemsById[it.itemId] }.take(3)
    val featuredEntryKeys = setOf("your-videos", "movies")
    val secondaryEntries = entries.filterNot {
        it.key in featuredEntryKeys || it.key == "settings" || it.key == "history" || it.key == "playlists"
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            item {
                YouHeader(actions = actions, onActionSelected = onActionSelected, onEntrySelected = onEntrySelected)
                Spacer(modifier = Modifier.height(18.dp))
                AccountSummary()
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader(
                    title = "History",
                    action = "",
                    onClick = { onEntrySelected("history") },
                    showTitleArrow = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    historyPreviewItems.forEach { item ->
                        HistoryPreviewCard(
                            item = item,
                            onClick = { onFeedItemSelected(item.id) },
                            onMoreClick = { historyMenuItemId = item.id }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader(
                    title = "Playlists",
                    action = "",
                    onClick = { onEntrySelected("playlists") },
                    showTitleArrow = true,
                    accessoryIcon = AddIcon,
                    onAccessoryClick = { onEntrySelected("playlists") }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    playlistPreviews.forEach { preview ->
                        val detail = playlistDetails.firstOrNull { it.key == preview.key }
                        val coverItem = detail?.itemIds?.firstOrNull()?.let(itemsById::get)
                        PlaylistCard(preview = preview, coverItem = coverItem, onClick = { onEntrySelected(preview.key) })
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                featuredEntryKeys.forEach { key ->
                    entries.firstOrNull { it.key == key }?.let { entry ->
                        FeatureEntryRow(entry = entry, onClick = { onEntrySelected(entry.key) })
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                PremiumCard()
                Spacer(modifier = Modifier.height(18.dp))
            }

            items(secondaryEntries, key = YouMenuEntry::key) { entry ->
                SecondaryEntryRow(entry = entry, onClick = { onEntrySelected(entry.key) })
            }

            item { Spacer(modifier = Modifier.height(84.dp)) }
        }

        if (historyMenuItemId != null) {
            OverflowActionsSheet(
                actions = historyOverflowActions,
                onActionSelected = { actionKey ->
                    val itemId = historyMenuItemId ?: return@OverflowActionsSheet
                    onHistoryOverflowAction(itemId, actionKey)
                    historyMenuItemId = null
                },
                onDismiss = { historyMenuItemId = null }
            )
        }
    }
}

@Composable
private fun YouHeader(
    actions: List<com.example.youtube_sim.model.HeaderAction>,
    onActionSelected: (String) -> Unit,
    onEntrySelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(shape = RoundedCornerShape(24.dp), color = Color(0xFFF4F4F5)) {
            Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Accounts")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = ChevronDownIcon, contentDescription = null, modifier = Modifier.size(18.dp))
            }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                actions.forEach { action ->
                Surface(modifier = Modifier.clickable { onActionSelected(action.key) }, shape = CircleShape, color = Color(0xFFF4F4F5)) {
                    Icon(imageVector = headerActionIcon(action.key), contentDescription = action.label, modifier = Modifier.padding(10.dp).size(20.dp))
                }
            }
            Surface(modifier = Modifier.clickable { onEntrySelected("settings") }, shape = CircleShape, color = Color(0xFFF4F4F5)) {
                Icon(imageVector = SettingsIcon, contentDescription = "Settings", modifier = Modifier.padding(10.dp).size(20.dp))
            }
        }
    }
}

@Composable
private fun AccountSummary() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(70.dp).background(Color(0xFFD81B60), CircleShape), contentAlignment = Alignment.Center) {
            Text(text = "E", color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = "Eddy", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Create a channel", color = Color(0xFF6B7280))
                Icon(imageVector = ChevronRightIcon, contentDescription = null, tint = Color(0xFF6B7280), modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun HistoryPreviewCard(
    item: FeedItem,
    onClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    Column(modifier = Modifier.width(156.dp).clickable(onClick = onClick)) {
        AssetThumbnail(item = item, modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f)) {
            item.actionText?.let { duration ->
                Surface(modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp), shape = RoundedCornerShape(4.dp), color = Color(0xD9000000)) {
                    Text(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), text = duration, color = Color.White, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.Top) {
            Text(text = item.title, modifier = Modifier.weight(1f), maxLines = 2, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodyMedium)
            Icon(imageVector = MoreIcon, contentDescription = "More", tint = Color(0xFF6B7280), modifier = Modifier.size(18.dp).clickable(onClick = onMoreClick))
        }
        Text(text = item.creator, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
    }
}
