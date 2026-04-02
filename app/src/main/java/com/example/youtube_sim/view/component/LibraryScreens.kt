package com.example.youtube_sim.view.component

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.HistorySection
import com.example.youtube_sim.model.PlaylistDetail
import com.example.youtube_sim.model.RootTab

@Composable
fun HistoryScreen(
    sections: List<HistorySection>,
    itemsById: Map<String, FeedItem>,
    onFeedItemSelected: (String) -> Unit,
    onBack: () -> Unit,
    onBottomTabSelected: (RootTab) -> Unit,
    onPlaceholderRequested: (String, String) -> Unit
) {
    LibraryScaffold(onBottomTabSelected = onBottomTabSelected) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            item {
                LibraryTopBar(title = "History", onBack = onBack, onPlaceholderRequested = onPlaceholderRequested)
                Spacer(modifier = Modifier.height(16.dp))
                Surface(shape = RoundedCornerShape(14.dp), color = Color(0xFFF4F4F5)) {
                    Text(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                        text = "Search watch history",
                        color = Color(0xFF6B7280)
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                FilterChips(labels = listOf("All", "Videos", "Shorts", "Music"))
                Spacer(modifier = Modifier.height(18.dp))
            }

            sections.forEach { section ->
                item {
                    Text(text = section.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                }
                items(section.entries, key = { "${section.title}-${it.itemId}" }) { entry ->
                    val item = itemsById[entry.itemId] ?: return@items
                    LibraryVideoRow(item = item, note = entry.note, onClick = { onFeedItemSelected(item.id) })
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }
        }
    }
}

@Composable
fun PlaylistScreen(
    playlist: PlaylistDetail,
    itemsById: Map<String, FeedItem>,
    onFeedItemSelected: (String) -> Unit,
    onBack: () -> Unit,
    onBottomTabSelected: (RootTab) -> Unit,
    onPlaceholderRequested: (String, String) -> Unit
) {
    LibraryScaffold(onBottomTabSelected = onBottomTabSelected) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            item {
                LibraryTopBar(title = "", onBack = onBack, onPlaceholderRequested = onPlaceholderRequested)
                Spacer(modifier = Modifier.height(8.dp))
                PlaylistHeader(playlist = playlist, itemsById = itemsById)
                Spacer(modifier = Modifier.height(18.dp))
                Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), color = Color.Black) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        text = "Play all",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(18.dp))
            }

            items(playlist.itemIds, key = { "${playlist.key}-$it" }) { itemId ->
                val item = itemsById[itemId] ?: return@items
                LibraryVideoRow(item = item, onClick = { onFeedItemSelected(item.id) })
                Spacer(modifier = Modifier.height(14.dp))
            }
        }
    }
}

@Composable
private fun PlaylistHeader(
    playlist: PlaylistDetail,
    itemsById: Map<String, FeedItem>
) {
    val firstItem = itemsById[playlist.itemIds.firstOrNull()]
    val colors = listOf(
        firstItem?.accentStart?.toScreenColor() ?: Color(0xFF27272A),
        firstItem?.accentEnd?.toScreenColor() ?: Color(0xFF52525B)
    )
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(Brush.linearGradient(colors))
                .aspectRatio(1.25f),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                modifier = Modifier.padding(18.dp),
                text = playlist.title,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(text = playlist.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = playlist.metadata, color = Color(0xFF6B7280))
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = playlist.description, color = Color(0xFF6B7280))
    }
}

@Composable
private fun LibraryVideoRow(
    item: FeedItem,
    note: String? = null,
    onClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick), verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .width(156.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.linearGradient(listOf(item.accentStart.toScreenColor(), item.accentEnd.toScreenColor())))
                .aspectRatio(16f / 9f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = item.thumbnailLabel, color = Color.White, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.creator, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
            Text(text = item.metadata, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
            note?.let {
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = it, style = MaterialTheme.typography.bodySmall, color = Color(0xFF9CA3AF))
            }
        }
        Text(text = "⋮", color = Color(0xFF737373))
    }
}

private fun String.toScreenColor(): Color {
    return runCatching { Color(parseColor(this)) }.getOrDefault(Color(0xFF52525B))
}
