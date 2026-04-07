package com.example.youtube_sim.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.ChannelProfile
import com.example.youtube_sim.model.FeedItem

private val channelTabs = listOf("Videos", "Playlists", "Community")
@Composable
fun ChannelScreen(
    profile: ChannelProfile,
    heroItem: FeedItem?,
    featuredItem: FeedItem?,
    videos: List<FeedItem>,
    isSubscribed: Boolean,
    onSubscriptionToggle: () -> Unit,
    onFeedItemSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        item {
            ChannelHero(profile = profile, heroItem = heroItem, onBack = onBack)
            ChannelHeader(profile = profile, isSubscribed = isSubscribed, onSubscriptionToggle = onSubscriptionToggle)
            ChannelTabs()
            featuredItem?.let {
                Spacer(modifier = Modifier.height(16.dp))
                FeaturedVideoCard(item = it, onClick = { onFeedItemSelected(it.id) })
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Uploads",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(videos, key = FeedItem::id) { item ->
            ChannelVideoRow(item = item, onClick = { onFeedItemSelected(item.id) })
            Spacer(modifier = Modifier.height(14.dp))
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
private fun ChannelHero(
    profile: ChannelProfile,
    heroItem: FeedItem?,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(216.dp)
    ) {
        if (heroItem != null) {
            AssetThumbnail(
                item = heroItem,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF0F172A), Color(0xFF7C3AED), Color(0xFFE11D48))
                        )
                    )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x66000000))
        )

        Surface(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .clickable(onClick = onBack),
            shape = CircleShape,
            color = Color(0xAA111827)
        ) {
            Icon(
                imageVector = BackIcon,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.padding(10.dp).size(20.dp)
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp),
            text = profile.title,
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ChannelHeader(
    profile: ChannelProfile,
    isSubscribed: Boolean,
    onSubscriptionToggle: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = CircleShape, color = Color(0xFF111827)) {
                Text(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
                    text = profile.title.take(2),
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = profile.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(text = profile.handle, color = Color(0xFF6B7280))
                Text(text = profile.subscribers, color = Color(0xFF6B7280))
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(text = profile.description, color = Color(0xFF4B5563), style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
            modifier = Modifier.clickable(onClick = onSubscriptionToggle),
            shape = RoundedCornerShape(999.dp),
            color = if (isSubscribed) Color(0xFFE5E7EB) else Color.Black
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 11.dp),
                text = if (isSubscribed) "Subscribed" else "Subscribe",
                color = if (isSubscribed) Color(0xFF111827) else Color.White,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun ChannelTabs() {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        channelTabs.forEachIndexed { index, label ->
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (index == 0) Color.Black else Color(0xFFF4F4F5)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = label,
                    color = if (index == 0) Color.White else Color(0xFF111827),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun FeaturedVideoCard(
    item: FeedItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick)
    ) {
        Text(text = "Featured", style = MaterialTheme.typography.labelLarge, color = Color(0xFF6B7280))
        Spacer(modifier = Modifier.height(10.dp))
        AssetThumbnail(
            item = item,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        ) {
            item.actionText?.let { duration ->
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp),
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xD9000000)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        text = duration,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Composable
private fun ChannelVideoRow(
    item: FeedItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        AssetThumbnail(
            item = item,
            modifier = Modifier
                .width(162.dp)
                .aspectRatio(16f / 9f)
        ) {
            item.actionText?.let { duration ->
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp),
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xD9000000)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        text = duration,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
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
            Text(text = item.metadata, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
        }
    }
}
