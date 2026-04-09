package com.example.youtube_sim.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.PlaylistDetail
import com.example.youtube_sim.model.PlaylistPreview

@Composable
fun PlaylistsOverviewScreen(
    playlistPreviews: List<PlaylistPreview>,
    playlistDetails: List<PlaylistDetail>,
    itemsById: Map<String, FeedItem>,
    onPlaylistSelected: (String) -> Unit,
    onBack: () -> Unit,
    onCreatePlaylist: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        PlaylistsTopBar(onBack = onBack)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Playlists",
            color = Color(0xFF111827),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(14.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FilterChip(label = "Recently added", selected = true, trailingIcon = ChevronDownIcon)
            FilterChip(label = "Playlists", selected = false)
            FilterChip(label = "Owned", selected = false)
        }
        Spacer(modifier = Modifier.height(16.dp))
        playlistPreviews.forEach { preview ->
            val detail = playlistDetails.firstOrNull { it.key == preview.key } ?: return@forEach
            val coverItem = detail.itemIds.firstOrNull()?.let(itemsById::get)
            PlaylistOverviewRow(
                preview = preview,
                coverItem = coverItem,
                onClick = { onPlaylistSelected(preview.key) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onCreatePlaylist),
            shape = RoundedCornerShape(999.dp),
            color = Color.Black
        ) {
            Text(
                modifier = Modifier.padding(vertical = 14.dp),
                text = "Create new playlist",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun PlaylistsTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.clickable(onClick = onBack),
            shape = CircleShape,
            color = Color(0xFFF4F4F5)
        ) {
            Icon(
                imageVector = BackIcon,
                contentDescription = "Back",
                tint = Color(0xFF111827),
                modifier = Modifier.padding(8.dp).size(20.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = CastIcon, contentDescription = "Cast", tint = Color(0xFF111827), modifier = Modifier.size(22.dp))
            Icon(imageVector = SearchIcon, contentDescription = "Search", tint = Color(0xFF111827), modifier = Modifier.size(22.dp))
            Icon(imageVector = MoreIcon, contentDescription = "More", tint = Color(0xFF111827), modifier = Modifier.size(22.dp))
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    trailingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (selected) Color.Black else Color(0xFFF4F4F5)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val contentColor = if (selected) Color.White else Color(0xFF111827)
            Text(text = label, color = contentColor, style = MaterialTheme.typography.labelLarge)
            trailingIcon?.let {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = it, contentDescription = null, tint = contentColor, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun PlaylistOverviewRow(
    preview: PlaylistPreview,
    coverItem: FeedItem?,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlaylistCard(preview = preview, coverItem = coverItem, onClick = onClick)
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = preview.title,
                color = Color(0xFF111827),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = preview.privacy, color = Color(0xFF6B7280), style = MaterialTheme.typography.bodyMedium)
        }
        Icon(imageVector = MoreIcon, contentDescription = "More", tint = Color(0xFF6B7280), modifier = Modifier.size(18.dp))
    }
}
