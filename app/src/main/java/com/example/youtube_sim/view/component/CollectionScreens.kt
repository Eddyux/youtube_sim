package com.example.youtube_sim.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.SettingsGroup
import com.example.youtube_sim.model.SubscriptionGroup

@Composable
fun SubscriptionsScreen(
    modifier: Modifier = Modifier,
    groups: List<SubscriptionGroup>,
    onChannelSelected: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        item {
            Text(text = "Subscriptions", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "New videos right to you", color = Color(0xFF6B7280))
            Spacer(modifier = Modifier.height(18.dp))
        }

        items(groups, key = SubscriptionGroup::title) { group ->
            Text(text = group.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            group.channels.forEach { channel ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFE5E7EB), CircleShape)
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                    ) {
                        Text(text = channel.name.take(1))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = buildString {
                                append(channel.name)
                                if (channel.verified) append(" ✓")
                            },
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(text = "Latest updates", style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
                    }
                    Surface(
                        modifier = Modifier.clickable { onChannelSelected(channel.name) },
                        shape = RoundedCornerShape(16.dp),
                        color = Color.Black
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            text = "All",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
        }

        item {
            Spacer(modifier = Modifier.height(84.dp))
        }
    }
}

@Composable
fun SettingsScreen(
    groups: List<SettingsGroup>,
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.clickable(onClick = onBack).padding(end = 12.dp),
                    text = "←",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = "Settings", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        items(groups, key = SettingsGroup::title) { group ->
            Text(text = group.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            group.items.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item.emoji)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = item.label)
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}
