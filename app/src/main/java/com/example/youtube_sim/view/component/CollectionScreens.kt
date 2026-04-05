package com.example.youtube_sim.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.example.youtube_sim.model.SubscriptionChannel
import com.example.youtube_sim.model.SubscriptionGroup

private val subscriptionTopics = listOf(
    "Beauty & Fashion",
    "Comedy",
    "Sports",
    "Music",
    "Tech",
    "Gaming",
    "Cooking & Health",
    "Film & Entertainment"
)

@Composable
fun SubscriptionsScreen(
    modifier: Modifier = Modifier,
    groups: List<SubscriptionGroup>,
    onChannelSelected: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA)),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            SubscriptionTopBar()
        }

        item {
            SubscriptionIntroCard()
        }

        item {
            TopicChipRow()
        }

        items(groups, key = SubscriptionGroup::title) { group ->
            SubscriptionGroupCard(
                group = group,
                onChannelSelected = onChannelSelected
            )
        }

        item {
            Spacer(modifier = Modifier.height(88.dp))
        }
    }
}

@Composable
private fun SubscriptionTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFFF1F1F))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = PlayLogoIcon,
                    contentDescription = "YouTube",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "YouTube", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TopActionPill(icon = CastIcon, description = "Cast")
            TopActionPill(icon = BellIcon, description = "Notifications")
            TopActionPill(icon = SearchIcon, description = "Search")
        }
    }
}

@Composable
private fun TopActionPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    description: String
) {
    Surface(shape = CircleShape, color = Color(0xFFF1F1F1)) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            modifier = Modifier.padding(10.dp).size(18.dp),
            tint = Color(0xFF111827)
        )
    }
}

@Composable
private fun SubscriptionIntroCard() {
    Surface(
        modifier = Modifier.padding(horizontal = 16.dp),
        shape = RoundedCornerShape(28.dp),
        color = Color.White,
        tonalElevation = 2.dp,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFFF8FAFC), Color(0xFFE0F2FE), Color(0xFFEDE9FE))
                    )
                )
                .padding(20.dp)
        ) {
            Text(
                text = "Subscriptions",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "New videos right to you",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF111827)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Follow creators by category and jump back into their latest uploads faster.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF4B5563)
            )
        }
    }
}

@Composable
private fun TopicChipRow() {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        subscriptionTopics.forEachIndexed { index, topic ->
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = if (index == 0) Color.Black else Color.White,
                tonalElevation = if (index == 0) 0.dp else 2.dp,
                shadowElevation = if (index == 0) 0.dp else 2.dp
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                    text = topic,
                    color = if (index == 0) Color.White else Color(0xFF111827),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun SubscriptionGroupCard(
    group: SubscriptionGroup,
    onChannelSelected: (String) -> Unit
) {
    Surface(
        modifier = Modifier.padding(horizontal = 16.dp),
        shape = RoundedCornerShape(26.dp),
        color = Color.White,
        tonalElevation = 2.dp,
        shadowElevation = 3.dp
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = group.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Recommended channels",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF6B7280)
                    )
                }
                Surface(shape = RoundedCornerShape(999.dp), color = Color(0xFFF3F4F6)) {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        text = "${group.channels.size} creators",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF374151)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            group.channels.forEachIndexed { index, channel ->
                SubscriptionChannelRow(channel = channel, onChannelSelected = onChannelSelected)
                if (index != group.channels.lastIndex) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun SubscriptionChannelRow(
    channel: SubscriptionChannel,
    onChannelSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(channelColor(channel.name)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = channel.name.take(1),
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = channel.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF111827)
            )
            Text(
                text = if (channel.verified) "Verified creator" else "Latest updates",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF6B7280)
            )
        }
        Surface(
            modifier = Modifier.clickable { onChannelSelected(channel.name) },
            shape = RoundedCornerShape(999.dp),
            color = Color.Black
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                text = "Subscribe",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

private fun channelColor(name: String): Color {
    val colors = listOf(
        Color(0xFFEF4444),
        Color(0xFF0EA5E9),
        Color(0xFF8B5CF6),
        Color(0xFFF59E0B),
        Color(0xFF10B981),
        Color(0xFFEC4899)
    )
    val index = name.fold(0) { acc, char -> acc + char.code } % colors.size
    return colors[index]
}
