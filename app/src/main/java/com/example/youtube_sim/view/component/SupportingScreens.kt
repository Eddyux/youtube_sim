package com.example.youtube_sim.view.component

import android.graphics.Color.parseColor
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.HeaderAction
import com.example.youtube_sim.model.HistoryPreview
import com.example.youtube_sim.model.HistorySection
import com.example.youtube_sim.model.PlaylistDetail
import com.example.youtube_sim.model.PlaylistPreview
import com.example.youtube_sim.model.YouMenuEntry

@Composable
fun PlaceholderScreen(
    title: String,
    description: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = OpenIcon, contentDescription = null, modifier = Modifier.size(34.dp))
            Spacer(modifier = Modifier.height(18.dp))
            Text(text = title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, style = MaterialTheme.typography.bodyLarge, color = Color(0xFF6B7280))
            Spacer(modifier = Modifier.height(18.dp))
            Surface(
                modifier = Modifier.clickable(onClick = onBack),
                shape = RoundedCornerShape(18.dp),
                color = Color.Black
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = BackIcon, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Back", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun YouScreen(
    modifier: Modifier = Modifier,
    actions: List<HeaderAction>,
    historyPreviews: List<HistoryPreview>,
    historySections: List<HistorySection>,
    playlistPreviews: List<PlaylistPreview>,
    playlistDetails: List<PlaylistDetail>,
    itemsById: Map<String, FeedItem>,
    entries: List<YouMenuEntry>,
    onActionSelected: (String) -> Unit,
    onEntrySelected: (String) -> Unit
) {
    val historyCoverItems = historySections.mapNotNull { section ->
        section.entries.firstOrNull()?.itemId?.let(itemsById::get)
    }
    val featuredEntryKeys = setOf("your-videos", "movies")
    val sectionEntryKeys = setOf("history", "playlists", "settings")
    val featuredEntries = entries.filter { it.key in featuredEntryKeys }
    val secondaryEntries = entries.filterNot { it.key in featuredEntryKeys || it.key in sectionEntryKeys }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(shape = RoundedCornerShape(24.dp), color = Color(0xFFF4F4F5)) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Accounts")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(imageVector = ChevronDownIcon, contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    actions.forEach { action ->
                        Surface(
                            modifier = Modifier.clickable { onActionSelected(action.key) },
                            shape = CircleShape,
                            color = Color(0xFFF4F4F5)
                        ) {
                            Icon(
                                imageVector = headerActionIcon(action.key),
                                contentDescription = action.label,
                                modifier = Modifier.padding(10.dp).size(20.dp)
                            )
                        }
                    }
                    Surface(
                        modifier = Modifier.clickable { onEntrySelected("settings") },
                        shape = CircleShape,
                        color = Color(0xFFF4F4F5)
                    ) {
                        Icon(
                            imageVector = SettingsIcon,
                            contentDescription = "Settings",
                            modifier = Modifier.padding(10.dp).size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFFD81B60))
                        .size(70.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "E", color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = "Eddy", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Create a channel", color = Color(0xFF6B7280))
                        Icon(
                            imageVector = ChevronRightIcon,
                            contentDescription = null,
                            tint = Color(0xFF6B7280),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(title = "History", action = "View all", onClick = { onEntrySelected("history") })
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                historyPreviews.forEachIndexed { index, preview ->
                    PreviewCard(
                        title = preview.title,
                        start = preview.accentStart,
                        end = preview.accentEnd,
                        item = historyCoverItems.getOrNull(index),
                        onClick = { onEntrySelected("history") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(
                title = "Playlists",
                action = "View all",
                onClick = { onEntrySelected("playlists") },
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
                CreatePlaylistCard(onClick = { onEntrySelected("playlists") })
            }

            Spacer(modifier = Modifier.height(24.dp))

            featuredEntries.forEach { entry ->
                FeatureEntryRow(entry = entry, onClick = { onEntrySelected(entry.key) })
            }

            if (featuredEntries.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
            }

            PremiumCard()
            Spacer(modifier = Modifier.height(18.dp))
        }

        items(secondaryEntries, key = YouMenuEntry::key) { entry ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEntrySelected(entry.key) }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = youEntryIcon(entry.key),
                    contentDescription = entry.label,
                    modifier = Modifier.size(22.dp),
                    tint = Color(0xFF111827)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = entry.label, fontWeight = FontWeight.SemiBold)
                    Text(text = entry.subtitle, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
                }
                Icon(imageVector = ChevronRightIcon, contentDescription = null, tint = Color(0xFF6B7280), modifier = Modifier.size(18.dp))
            }
        }

        item { Spacer(modifier = Modifier.height(84.dp)) }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    action: String,
    onClick: () -> Unit,
    accessoryIcon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    onAccessoryClick: (() -> Unit)? = null
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))
        if (accessoryIcon != null && onAccessoryClick != null) {
            Surface(
                modifier = Modifier.clickable(onClick = onAccessoryClick),
                shape = CircleShape,
                color = Color.Transparent
            ) {
                Icon(
                    imageVector = accessoryIcon,
                    contentDescription = null,
                    modifier = Modifier.padding(6.dp).size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
        }
        Surface(
            modifier = Modifier.clickable(onClick = onClick),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFF4F4F5)
        ) {
            Text(modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp), text = action)
        }
    }
}

@Composable
private fun PreviewCard(
    title: String,
    start: String,
    end: String,
    item: FeedItem?,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.width(146.dp).clickable(onClick = onClick)) {
        if (item != null) {
            AssetThumbnail(
                item = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.linearGradient(listOf(start.toColor(), end.toColor())))
                    .aspectRatio(1f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, maxLines = 2, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun PlaylistCard(
    preview: PlaylistPreview,
    coverItem: FeedItem?,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.width(156.dp).clickable(onClick = onClick)) {
        if (coverItem != null) {
            AssetThumbnail(
                item = coverItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f)
            ) {
                Surface(
                    modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp),
                    shape = RoundedCornerShape(999.dp),
                    color = Color(0xB3000000)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        text = preview.count,
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF6B7280))
                    .aspectRatio(1.2f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = preview.count, color = Color.White, style = MaterialTheme.typography.headlineSmall)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = preview.title, fontWeight = FontWeight.SemiBold)
        Text(text = preview.privacy, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
    }
}

@Composable
private fun CreatePlaylistCard(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(92.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.2f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF4F4F5)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = AddIcon, contentDescription = "New playlist", modifier = Modifier.size(26.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "New", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun FeatureEntryRow(
    entry: YouMenuEntry,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFFF4F4F5)
        ) {
            Icon(
                imageVector = youEntryIcon(entry.key),
                contentDescription = entry.label,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp).size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = entry.label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Icon(imageVector = ChevronRightIcon, contentDescription = null, tint = Color(0xFF6B7280), modifier = Modifier.size(18.dp))
    }
}

@Composable
private fun PremiumCard() {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFF8F8F8),
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFFFEEEE)
            ) {
                Icon(
                    imageVector = PremiumBadgeIcon,
                    contentDescription = "Premium",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp).size(18.dp),
                    tint = Color(0xFFFF1F1F)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Try YouTube Premium for NT$0",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Restrictions apply. Cancel anytime.",
                    color = Color(0xFF6B7280),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

private fun String.toColor(): Color {
    return runCatching { Color(parseColor(this)) }.getOrDefault(Color(0xFF6B7280))
}
