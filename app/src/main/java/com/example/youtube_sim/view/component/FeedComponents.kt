package com.example.youtube_sim.view.component

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.FeedItemType
import com.example.youtube_sim.model.HeaderAction
import com.example.youtube_sim.model.HomeChip
import com.example.youtube_sim.model.HomeTabContent

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeTabs: List<HomeTabContent>,
    selectedChipKey: String,
    chips: List<HomeChip>,
    actions: List<HeaderAction>,
    onChipSelected: (String) -> Unit,
    onActionSelected: (String) -> Unit,
    onFeedItemSelected: (String) -> Unit
) {
    val activeTab = homeTabs.firstOrNull { it.key == selectedChipKey } ?: homeTabs.firstOrNull()
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        item {
            YoutubeTopBar(actions = actions, onActionSelected = onActionSelected)
            HomeChipRow(
                chips = chips,
                selectedKey = activeTab?.key ?: selectedChipKey,
                onChipSelected = onChipSelected
            )
            Spacer(modifier = Modifier.height(14.dp))
        }

        activeTab?.items?.let { items ->
            items(items, key = FeedItem::id) { item ->
                FeedItemSection(item = item, onClick = { onFeedItemSelected(item.title) })
                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(84.dp))
        }
    }
}

@Composable
private fun FeedItemSection(
    item: FeedItem,
    onClick: () -> Unit
) {
    Column {
        item.sectionTitle?.let {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                text = it,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        when (item.type) {
            FeedItemType.AD -> AdCard(item = item, onClick = onClick)
            FeedItemType.PROMO -> PromoCard(item = item, onClick = onClick)
            FeedItemType.VIDEO,
            FeedItemType.LIVE -> VideoCard(item = item, onClick = onClick)
        }
    }
}

@Composable
private fun AdCard(item: FeedItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp)
    ) {
        Text(text = item.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        ThumbnailBox(item = item)
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Avatar(text = "Ad")
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.creator, fontWeight = FontWeight.SemiBold)
                item.supportingText?.let {
                    Text(text = it, style = MaterialTheme.typography.bodySmall, color = Color(0xFF5F6368))
                }
            }
            Surface(shape = RoundedCornerShape(18.dp), color = Color(0xFF111827)) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp),
                    text = item.actionText ?: "Open",
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun PromoCard(item: FeedItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(item.accentStart.toColor(), item.accentEnd.toColor())
                )
            )
            .clickable(onClick = onClick)
            .aspectRatio(16f / 9f)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = item.title,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = item.metadata,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(8.dp))
            item.supportingText?.let {
                Text(
                    text = it,
                    color = Color(0xFFE5E7EB),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun VideoCard(item: FeedItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        ThumbnailBox(item = item)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Avatar(text = item.creator.take(1))
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = item.creator, style = MaterialTheme.typography.bodySmall, color = Color(0xFF737373))
                Text(text = item.metadata, style = MaterialTheme.typography.bodySmall, color = Color(0xFF737373))
            }
            Text(text = "⋮", color = Color(0xFF737373))
        }
    }
}

@Composable
private fun ThumbnailBox(
    item: FeedItem,
    onClick: (() -> Unit)? = null
) {
    val baseModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .clip(RoundedCornerShape(18.dp))
        .background(
            Brush.linearGradient(
                colors = listOf(item.accentStart.toColor(), item.accentEnd.toColor())
            )
        )
        .aspectRatio(16f / 9f)

    Box(modifier = if (onClick == null) baseModifier else baseModifier.clickable(onClick = onClick)) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 18.dp),
            text = item.thumbnailLabel,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        item.badgeText?.let {
            BadgePill(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
                text = it,
                background = Color(0xFFFF2D2D)
            )
        }
        item.actionText?.let {
            BadgePill(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp),
                text = it,
                background = Color(0xD9000000)
            )
        }
    }
}

@Composable
private fun BadgePill(
    modifier: Modifier = Modifier,
    text: String,
    background: Color
) {
    Surface(modifier = modifier, shape = RoundedCornerShape(8.dp), color = background) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun Avatar(text: String) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(Color(0xFFE5E7EB)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
    }
}

private fun String.toColor(): Color {
    return runCatching { Color(parseColor(this)) }.getOrDefault(Color(0xFF374151))
}
