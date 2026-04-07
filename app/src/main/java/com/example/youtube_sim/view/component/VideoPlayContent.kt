package com.example.youtube_sim.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.VideoComment

@Composable
fun VideoPlayContent(
    item: FeedItem,
    relatedItems: List<FeedItem>,
    comments: List<VideoComment>,
    isCreatorSubscribed: Boolean,
    onFeedItemSelected: (String) -> Unit,
    onChannelSelected: (String) -> Unit,
    onSubscriptionToggle: () -> Unit,
    onBack: () -> Unit,
    onSettingsClick: () -> Unit,
    onCommentsClick: () -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { VideoPlayerArea(item = item, onBack = onBack, onSettingsClick = onSettingsClick) }
        item {
            VideoInfoSection(
                item = item,
                isCreatorSubscribed = isCreatorSubscribed,
                onChannelSelected = onChannelSelected,
                onSubscriptionToggle = onSubscriptionToggle
            )
        }
        item { ActionButtonsRow() }
        item { CommentsPreview(comments = comments, onClick = onCommentsClick) }
        items(relatedItems, key = FeedItem::id) { related ->
            RelatedVideoRow(item = related, onClick = { onFeedItemSelected(related.id) })
            Spacer(modifier = Modifier.height(12.dp))
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
private fun VideoPlayerArea(
    item: FeedItem,
    onBack: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .background(Color.Black)
    ) {
        AssetVideoPlayer(
            item = item,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            showControls = true,
            keepControlsVisible = true
        ) {
            Surface(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .clickable(onClick = onBack),
                shape = CircleShape,
                color = Color(0xB3000000)
            ) {
                Icon(
                    imageVector = BackIcon,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.padding(10.dp).size(22.dp)
                )
            }
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .clickable(onClick = onSettingsClick),
                shape = CircleShape,
                color = Color(0xB3000000)
            ) {
                Icon(
                    imageVector = SettingsIcon,
                    contentDescription = "Playback settings",
                    tint = Color.White,
                    modifier = Modifier.padding(10.dp).size(22.dp)
                )
            }
            item.actionText?.let { duration ->
                Surface(
                    modifier = Modifier.align(Alignment.BottomEnd).padding(12.dp),
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
private fun VideoInfoSection(
    item: FeedItem,
    isCreatorSubscribed: Boolean,
    onChannelSelected: (String) -> Unit,
    onSubscriptionToggle: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = item.metadata, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth().clickable { onChannelSelected(item.creator) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(36.dp).clip(CircleShape).background(Color(0xFFE5E7EB)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = item.creator.take(1), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = item.creator,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Surface(
                modifier = Modifier.clickable(onClick = onSubscriptionToggle),
                shape = RoundedCornerShape(18.dp),
                color = if (isCreatorSubscribed) Color(0xFFE5E7EB) else Color.Black
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = if (isCreatorSubscribed) "Subscribed" else "Subscribe",
                    color = if (isCreatorSubscribed) Color(0xFF111827) else Color.White,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun ActionButtonsRow() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButton(LikeIcon, "Like", "53")
        ActionButton(DislikeIcon, "Dislike")
        ActionButton(ShareIcon, "Share")
        ActionButton(SaveIcon, "Save")
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun ActionButton(
    icon: ImageVector,
    label: String,
    trailingText: String = ""
) {
    Surface(shape = RoundedCornerShape(20.dp), color = Color(0xFFF1F1F1)) {
        Row(modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = label, modifier = Modifier.size(18.dp))
            if (trailingText.isNotEmpty()) {
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = trailingText, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
private fun CommentsPreview(comments: List<VideoComment>, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = "Comments ${comments.size}", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        comments.firstOrNull()?.let { comment ->
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier.size(28.dp).clip(CircleShape).background(Color(0xFFE5E7EB)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = comment.author.take(1), style = MaterialTheme.typography.labelSmall)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = comment.text,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun RelatedVideoRow(item: FeedItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 16.dp)
    ) {
        AssetThumbnail(
            item = item,
            modifier = Modifier
                .width(160.dp)
                .aspectRatio(16f / 9f)
        ) {
            item.actionText?.let { duration ->
                Surface(
                    modifier = Modifier.align(Alignment.BottomEnd).padding(4.dp),
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xD9000000)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                        text = duration,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, style = MaterialTheme.typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.creator, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
            Text(text = item.metadata, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
        }
        Icon(imageVector = MoreIcon, contentDescription = "More", tint = Color(0xFF737373), modifier = Modifier.size(18.dp))
    }
}
