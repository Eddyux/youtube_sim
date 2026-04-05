package com.example.youtube_sim.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.NotificationsOff
import androidx.compose.material.icons.rounded.SmartDisplay
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
            .background(Color(0xFF121212))
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        item {
            ScreenTitleRow(title = "General", onBack = onBack)
            Spacer(modifier = Modifier.height(20.dp))
        }

        items(items, key = GeneralSettingsItem::key) { item ->
            SettingsToggleRow(
                title = item.label,
                subtitle = item.subtitle,
                icon = generalSettingIcon(item.key),
                hasToggle = item.hasToggle,
                checked = toggleStates[item.key] ?: false,
                onToggle = { onToggle(item.key) },
                darkMode = true
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
            .background(Color(0xFF121212))
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        item {
            ScreenTitleRow(title = "Notifications", onBack = onBack)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Mobile notifications",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        items(items, key = NotificationSettingsItem::key) { item ->
            SettingsToggleRow(
                title = item.label,
                subtitle = item.description,
                icon = notificationSettingIcon(item.key),
                hasToggle = item.hasToggle,
                checked = toggleStates[item.key] ?: false,
                onToggle = { onToggle(item.key) },
                darkMode = true
            )
        }
    }
}

@Composable
private fun ScreenTitleRow(
    title: String,
    onBack: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            modifier = Modifier.clickable(onClick = onBack),
            shape = CircleShape,
            color = Color(0xFF262626)
        ) {
            Icon(
                imageVector = BackIcon,
                contentDescription = "Back",
                modifier = Modifier.padding(8.dp).size(20.dp),
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun SettingsToggleRow(
    title: String,
    subtitle: String?,
    icon: ImageVector,
    hasToggle: Boolean,
    checked: Boolean,
    onToggle: () -> Unit,
    darkMode: Boolean
) {
    val titleColor = if (darkMode) Color.White else Color.Black
    val subtitleColor = if (darkMode) Color(0xFF9CA3AF) else Color(0xFF6B7280)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = if (darkMode) Color.White else Color.Black,
            modifier = Modifier.padding(top = 2.dp).size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = titleColor, style = MaterialTheme.typography.bodyLarge)
            subtitle?.let {
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = it, color = subtitleColor, style = MaterialTheme.typography.bodySmall)
            }
        }
        if (hasToggle) {
            Spacer(modifier = Modifier.width(12.dp))
            Switch(
                checked = checked,
                onCheckedChange = { onToggle() },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Color(0xFF3B82F6),
                    uncheckedTrackColor = if (darkMode) Color(0xFF4B5563) else Color(0xFFD1D5DB)
                )
            )
        }
    }
}

private fun generalSettingIcon(key: String): ImageVector = when (key) {
    "appearance" -> PaletteIcon
    "rotate_shorts" -> RotateIcon
    "playback_in_feeds" -> PlayLogoIcon
    "voice_search_language" -> LanguageIcon
    "location" -> LocationIcon
    "restricted_mode" -> RestrictedIcon
    "stats_for_nerds" -> StatsIcon
    "earn_badges" -> BadgesIcon
    else -> SettingsIcon
}

private fun notificationSettingIcon(key: String): ImageVector = when (key) {
    "scheduled_digest" -> BellIcon
    "subscriptions" -> BellIcon
    "channel_settings" -> SettingsIcon
    "recommended_videos" -> Icons.Rounded.SmartDisplay
    "recommended_create" -> Icons.Rounded.LightMode
    "activity_channel" -> CommentIcon
    "activity_comments" -> CommentIcon
    "mentions" -> Icons.Rounded.AlternateEmail
    "reused_content" -> ShareIcon
    "product_updates" -> InfoIcon
    "promotional_content" -> PlayLogoIcon
    "watch_on_tv" -> CastIcon
    "disable_sounds" -> Icons.Rounded.NotificationsOff
    "new_badges" -> BadgesIcon
    else -> BellIcon
}
