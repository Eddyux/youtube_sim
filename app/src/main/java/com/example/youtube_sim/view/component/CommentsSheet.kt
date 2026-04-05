package com.example.youtube_sim.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.VideoComment

@Composable
fun CommentsSheet(
    comments: List<VideoComment>,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.78f)
                .clickable(enabled = false, onClick = {}),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = Color(0xFF171717)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Comments",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(imageVector = InfoIcon, contentDescription = null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(
                        modifier = Modifier.clickable(onClick = onDismiss),
                        shape = CircleShape,
                        color = Color(0xFF262626)
                    ) {
                        Icon(
                            imageVector = CloseIcon,
                            contentDescription = "Close",
                            tint = Color.White,
                            modifier = Modifier.padding(8.dp).size(18.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CommentChip(label = "Top", selected = true)
                    CommentChip(label = "Newest", selected = false)
                }
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 20.dp)
                ) {
                    items(comments, key = VideoComment::handle) { comment ->
                        CommentRow(comment = comment)
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                }
                Surface(color = Color(0xFF171717), tonalElevation = 6.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF374151)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "E", color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFF262626)
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                text = "Add a comment...",
                                color = Color(0xFF9CA3AF)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = SendIcon, contentDescription = "Send", tint = Color(0xFF9CA3AF), modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun CommentChip(label: String, selected: Boolean) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = if (selected) Color.White else Color(0xFF262626)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            text = label,
            color = if (selected) Color.Black else Color.White
        )
    }
}

@Composable
private fun CommentRow(comment: VideoComment) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFF374151)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = comment.author.take(1), color = Color.White, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = comment.handle, color = Color.White, fontWeight = FontWeight.SemiBold)
                Text(text = comment.timeAgo, color = Color(0xFF9CA3AF), style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = comment.text, color = Color.White, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = LikeIcon, contentDescription = "Like", tint = Color(0xFF9CA3AF), modifier = Modifier.size(16.dp))
                Text(text = comment.likes, color = Color(0xFF9CA3AF), style = MaterialTheme.typography.bodySmall)
                Icon(imageVector = DislikeIcon, contentDescription = "Dislike", tint = Color(0xFF9CA3AF), modifier = Modifier.size(16.dp))
                Icon(imageVector = CommentIcon, contentDescription = "Reply", tint = Color(0xFF9CA3AF), modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Translate to English", color = Color(0xFF93C5FD), style = MaterialTheme.typography.bodySmall)
            comment.replyCount?.let {
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = it, color = Color(0xFF93C5FD), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
