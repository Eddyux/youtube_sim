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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.HeaderAction
import com.example.youtube_sim.model.HistoryPreview
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
            Text(text = "🚧", style = MaterialTheme.typography.displayMedium)
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
                Text(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                    text = "返回",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun SectionLandingScreen(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    emoji: String
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = emoji, style = MaterialTheme.typography.displayMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, style = MaterialTheme.typography.bodyLarge, color = Color(0xFF6B7280))
        }
    }
}

@Composable
fun YouScreen(
    modifier: Modifier = Modifier,
    actions: List<HeaderAction>,
    historyPreviews: List<HistoryPreview>,
    playlistPreviews: List<PlaylistPreview>,
    entries: List<YouMenuEntry>,
    onActionSelected: (String) -> Unit,
    onEntrySelected: (String) -> Unit
) {
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
                    Text(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp), text = "Accounts ▼")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    actions.forEach { action ->
                        Surface(
                            modifier = Modifier.clickable { onActionSelected(action.key) },
                            shape = CircleShape,
                            color = Color(0xFFF4F4F5)
                        ) {
                            Text(modifier = Modifier.padding(10.dp), text = action.emoji)
                        }
                    }
                    Surface(
                        modifier = Modifier.clickable { onEntrySelected("settings") },
                        shape = CircleShape,
                        color = Color(0xFFF4F4F5)
                    ) {
                        Text(modifier = Modifier.padding(10.dp), text = "⚙️")
                    }
                }
            }
            Spacer(modifier = Modifier.height(18.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFFD81B60))
                        .padding(horizontal = 18.dp, vertical = 14.dp)
                ) {
                    Text(text = "E", color = Color.White, style = MaterialTheme.typography.titleLarge)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = "Eddy", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text(text = "Create a channel >", color = Color(0xFF6B7280))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(title = "History", action = "View all")
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                historyPreviews.forEach { preview ->
                    PreviewCard(title = preview.title, start = preview.accentStart, end = preview.accentEnd)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(title = "Playlists", action = "View all")
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                playlistPreviews.forEach { preview ->
                    PlaylistCard(preview = preview)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        items(entries, key = YouMenuEntry::key) { entry ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEntrySelected(entry.key) }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = entry.emoji, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = entry.label, fontWeight = FontWeight.SemiBold)
                    Text(text = entry.subtitle, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
                }
                Text(text = "›", color = Color(0xFF6B7280))
            }
        }

        item {
            Spacer(modifier = Modifier.height(84.dp))
        }
    }
}

@Composable
private fun SectionHeader(title: String, action: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Surface(shape = RoundedCornerShape(16.dp), color = Color(0xFFF4F4F5)) {
            Text(modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp), text = action)
        }
    }
}

@Composable
private fun PreviewCard(title: String, start: String, end: String) {
    Column(modifier = Modifier.width(146.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Brush.linearGradient(listOf(start.toColor(), end.toColor())))
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, maxLines = 2, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun PlaylistCard(preview: PlaylistPreview) {
    Column(modifier = Modifier.width(156.dp)) {
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = preview.title, fontWeight = FontWeight.SemiBold)
        Text(text = preview.privacy, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
    }
}

private fun String.toColor(): Color {
    return runCatching { Color(parseColor(this)) }.getOrDefault(Color(0xFF6B7280))
}
