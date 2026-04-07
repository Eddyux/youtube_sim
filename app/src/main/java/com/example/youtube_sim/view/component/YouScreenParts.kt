package com.example.youtube_sim.view.component

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.PlaylistPreview
import com.example.youtube_sim.model.YouMenuEntry

@Composable
fun SectionHeader(
    title: String,
    action: String,
    onClick: () -> Unit,
    showTitleArrow: Boolean = false,
    accessoryIcon: ImageVector? = null,
    onAccessoryClick: (() -> Unit)? = null
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Row(
            modifier = if (showTitleArrow) Modifier.clickable(onClick = onClick) else Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            if (showTitleArrow) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = ChevronRightIcon,
                    contentDescription = null,
                    tint = Color(0xFF111827),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        if (accessoryIcon != null && onAccessoryClick != null) {
            Surface(modifier = Modifier.clickable(onClick = onAccessoryClick), shape = CircleShape, color = Color.Transparent) {
                Icon(imageVector = accessoryIcon, contentDescription = null, modifier = Modifier.padding(6.dp).size(22.dp))
            }
            Spacer(modifier = Modifier.width(6.dp))
        }
        if (action.isNotBlank()) {
            Surface(modifier = Modifier.clickable(onClick = onClick), shape = RoundedCornerShape(16.dp), color = Color(0xFFF4F4F5)) {
                Text(modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp), text = action)
            }
        }
    }
}

@Composable
fun PlaylistCard(
    preview: PlaylistPreview,
    coverItem: FeedItem?,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.width(156.dp).clickable(onClick = onClick)) {
        if (coverItem != null) {
            AssetThumbnail(item = coverItem, modifier = Modifier.fillMaxWidth().aspectRatio(1.2f)) {
                coverItem.actionText?.let { duration ->
                    Surface(modifier = Modifier.align(Alignment.BottomStart).padding(10.dp), shape = RoundedCornerShape(999.dp), color = Color(0xB3000000)) {
                        Text(modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), text = duration, color = Color.White, style = MaterialTheme.typography.labelSmall)
                    }
                }
                Surface(modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp), shape = RoundedCornerShape(999.dp), color = Color(0xB3000000)) {
                    Text(modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), text = preview.count, color = Color.White, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxWidth().aspectRatio(1.2f).background(Color(0xFF6B7280), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
                Text(text = preview.count, color = Color.White, style = MaterialTheme.typography.headlineSmall)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = preview.title, fontWeight = FontWeight.SemiBold)
        Text(text = preview.privacy, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
    }
}

@Composable
fun CreatePlaylistCard(onClick: () -> Unit) {
    Column(modifier = Modifier.width(92.dp).clickable(onClick = onClick), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.fillMaxWidth().aspectRatio(1.2f).background(Color(0xFFF4F4F5), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
            Icon(imageVector = AddIcon, contentDescription = "New playlist", modifier = Modifier.size(26.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "New", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun FeatureEntryRow(entry: YouMenuEntry, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Surface(shape = RoundedCornerShape(14.dp), color = Color(0xFFF4F4F5)) {
            Icon(imageVector = youEntryIcon(entry.key), contentDescription = entry.label, modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp).size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = entry.label, modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Icon(imageVector = ChevronRightIcon, contentDescription = null, tint = Color(0xFF6B7280), modifier = Modifier.size(18.dp))
    }
}

@Composable
fun SecondaryEntryRow(entry: YouMenuEntry, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = youEntryIcon(entry.key), contentDescription = entry.label, modifier = Modifier.size(22.dp), tint = Color(0xFF111827))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = entry.label, fontWeight = FontWeight.SemiBold)
            Text(text = entry.subtitle, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
        }
        Icon(imageVector = ChevronRightIcon, contentDescription = null, tint = Color(0xFF6B7280), modifier = Modifier.size(18.dp))
    }
}

@Composable
fun PremiumCard() {
    Surface(shape = RoundedCornerShape(18.dp), color = Color(0xFFF8F8F8), tonalElevation = 1.dp) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = RoundedCornerShape(12.dp), color = Color(0xFFFFEEEE)) {
                Icon(imageVector = PremiumBadgeIcon, contentDescription = "Premium", modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp).size(18.dp), tint = Color(0xFFFF1F1F))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Try YouTube Premium for NT$0", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Text(text = "Restrictions apply. Cancel anytime.", color = Color(0xFF6B7280), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
