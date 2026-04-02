package com.example.youtube_sim.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.GeneralSettingsItem
import com.example.youtube_sim.model.NotificationSettingsItem

@Composable
fun GeneralScreen(
    items: List<GeneralSettingsItem>,
    toggleStates: Map<String, Boolean>,
    onToggle: (String) -> Unit,
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.clickable(onClick = onBack).padding(end = 12.dp),
                    text = "←",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = "General", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        items(items, key = GeneralSettingsItem::key) { item ->
            SettingsToggleRow(
                title = item.label,
                subtitle = item.subtitle,
                hasToggle = item.hasToggle,
                checked = toggleStates[item.key] ?: false,
                onToggle = { onToggle(item.key) }
            )
        }
    }
}

@Composable
fun NotificationsScreen(
    items: List<NotificationSettingsItem>,
    toggleStates: Map<String, Boolean>,
    onToggle: (String) -> Unit,
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.clickable(onClick = onBack).padding(end = 12.dp),
                    text = "←",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Notifications",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Mobile notifications",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        items(items, key = NotificationSettingsItem::key) { item ->
            SettingsToggleRow(
                title = item.label,
                subtitle = item.description,
                hasToggle = item.hasToggle,
                checked = toggleStates[item.key] ?: false,
                onToggle = { onToggle(item.key) }
            )
        }
    }
}

@Composable
private fun SettingsToggleRow(
    title: String,
    subtitle: String?,
    hasToggle: Boolean,
    checked: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            subtitle?.let {
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = it, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
            }
        }
        if (hasToggle) {
            Spacer(modifier = Modifier.width(12.dp))
            Switch(
                checked = checked,
                onCheckedChange = { onToggle() },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Color(0xFF3B82F6),
                    uncheckedTrackColor = Color(0xFFD1D5DB)
                )
            )
        }
    }
}
