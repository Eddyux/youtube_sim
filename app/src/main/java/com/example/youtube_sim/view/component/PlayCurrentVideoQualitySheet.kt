package com.example.youtube_sim.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.QualityOption

@Composable
fun PlayCurrentVideoQualitySheet(
    options: List<QualityOption>,
    selectedKey: String,
    currentResolutionLabel: String,
    onOptionSelected: (String) -> Unit,
    onGlobalQualityClick: () -> Unit,
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
                .clickable(enabled = false, onClick = {}),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            color = Color(0xFF212121)
        ) {
            Column(modifier = Modifier.padding(top = 16.dp, bottom = 20.dp)) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quality for current video",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = currentResolutionLabel,
                        color = Color(0xFFA1A1AA),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.size(6.dp))
                options.forEach { option ->
                    CurrentVideoQualityRow(
                        option = option,
                        selected = selectedKey == option.key,
                        onClick = { onOptionSelected(option.key) }
                    )
                }
                HorizontalDivider(
                    color = Color(0xFF3F3F46),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = "This selection only applies to the current video.",
                    color = Color(0xFFA1A1AA),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "For all videos, go to ",
                        color = Color(0xFFA1A1AA),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Settings > Video Quality Preferences.",
                        color = Color(0xFF3EA6FF),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.clickable(onClick = onGlobalQualityClick)
                    )
                }
            }
        }
    }
}

@Composable
private fun CurrentVideoQualityRow(
    option: QualityOption,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier.size(24.dp),
            contentAlignment = Alignment.TopStart
        ) {
            if (selected) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Selected",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = option.label,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = option.description,
                color = Color(0xFFA1A1AA),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
