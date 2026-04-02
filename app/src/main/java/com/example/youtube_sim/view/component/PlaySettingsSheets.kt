package com.example.youtube_sim.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.PlaySettingsMenuItem

@Composable
fun PlaySettingsSheet(
    items: List<PlaySettingsMenuItem>,
    onDismiss: () -> Unit,
    onMoreClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().clickable(enabled = false, onClick = {}),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            color = Color(0xFF212121)
        ) {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (item.key == "more") onMoreClick() else onDismiss()
                            }
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item.emoji, color = Color.White)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = item.label,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        item.currentValue?.let {
                            Text(text = it, color = Color(0xFF9CA3AF), style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlaySettingsMoreSheet(
    items: List<PlaySettingsMenuItem>,
    toggleStates: Map<String, Boolean>,
    onToggle: (String) -> Unit,
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
            modifier = Modifier.fillMaxWidth().clickable(enabled = false, onClick = {}),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            color = Color(0xFF212121)
        ) {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.clickable(onClick = onDismiss).padding(end = 12.dp),
                        text = "←",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "More",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                items.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item.emoji, color = Color.White)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = item.label,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        if (item.hasToggle) {
                            Switch(
                                checked = toggleStates[item.key] ?: false,
                                onCheckedChange = { onToggle(item.key) },
                                colors = SwitchDefaults.colors(
                                    checkedTrackColor = Color(0xFF3B82F6),
                                    uncheckedTrackColor = Color(0xFF4B5563)
                                )
                            )
                        }
                        item.currentValue?.let {
                            Text(text = it, color = Color(0xFF9CA3AF), style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
