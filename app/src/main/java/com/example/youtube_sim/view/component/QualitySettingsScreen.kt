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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import com.example.youtube_sim.model.QualityOption
import com.example.youtube_sim.model.QualityPreferenceSection

@Composable
fun QualitySettingsScreen(
    sections: List<QualityPreferenceSection>,
    selectedOptions: Map<String, String>,
    onOptionSelected: (String, String) -> Unit,
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        item {
            LightQualityHeader(onBack = onBack)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Set your preferred streaming quality as your default setting for all videos. You can also change streaming quality in player settings for each video.",
                color = Color(0xFF6B7280),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(18.dp))
        }

        items(sections, key = QualityPreferenceSection::key) { section ->
            HorizontalDivider(color = Color(0xFFE5E7EB))
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = section.title,
                color = Color(0xFF111827),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            section.options.forEach { option ->
                QualityOptionRow(
                    option = option,
                    selected = selectedOptions[section.key] == option.key,
                    onClick = { onOptionSelected(section.key, option.key) }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun LightQualityHeader(onBack: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            modifier = Modifier.clickable(onClick = onBack),
            shape = androidx.compose.foundation.shape.CircleShape,
            color = Color(0xFFF1F5F9)
        ) {
            Icon(
                imageVector = BackIcon,
                contentDescription = "Back",
                modifier = Modifier.padding(8.dp).size(20.dp),
                tint = Color(0xFF111827)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Quality",
            color = Color(0xFF111827),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun QualityOptionRow(
    option: QualityOption,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = option.label,
                color = Color(0xFF111827),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = option.description,
                color = Color(0xFF6B7280),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF3EA6FF),
                unselectedColor = Color(0xFFD4D4D8)
            )
        )
    }
}
