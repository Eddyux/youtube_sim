package com.example.youtube_sim.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.youtube_sim.model.LanguageOption

@Composable
fun LanguageSettingsScreen(
    options: List<LanguageOption>,
    selectedKey: String,
    onLanguageSelected: (String) -> Unit,
    onPreferredLanguagesClick: () -> Unit,
    onLearnMoreClick: () -> Unit,
    onBack: () -> Unit
) {
    var showLanguageDialog by remember { mutableStateOf(false) }
    val selectedOption = options.firstOrNull { it.key == selectedKey } ?: options.firstOrNull()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        LightTitleRow(title = "Languages", onBack = onBack)
        Spacer(modifier = Modifier.height(28.dp))
        SettingsSummaryRow(
            title = "App language",
            subtitle = selectedOption?.label.orEmpty(),
            onClick = { showLanguageDialog = true }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onPreferredLanguagesClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Preferred Languages",
                    color = Color(0xFF111827),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Languages you want to watch your videos in.",
                    color = Color(0xFF6B7280),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Learn more",
                    color = Color(0xFF3EA6FF),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable(onClick = onLearnMoreClick)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                imageVector = ChevronRightIcon,
                contentDescription = "Preferred Languages",
                tint = Color(0xFF6B7280),
                modifier = Modifier.size(20.dp)
            )
        }
    }

    if (showLanguageDialog) {
        AppLanguageDialog(
            options = options,
            selectedKey = selectedKey,
            onOptionSelected = {
                onLanguageSelected(it)
                showLanguageDialog = false
            },
            onDismiss = { showLanguageDialog = false }
        )
    }
}

@Composable
private fun LightTitleRow(
    title: String,
    onBack: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            modifier = Modifier.clickable(onClick = onBack),
            shape = CircleShape,
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
            text = title,
            color = Color(0xFF111827),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun SettingsSummaryRow(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Text(
            text = title,
            color = Color(0xFF111827),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            color = Color(0xFF6B7280),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun AppLanguageDialog(
    options: List<LanguageOption>,
    selectedKey: String,
    onOptionSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val selectedOption = options.firstOrNull { it.key == selectedKey } ?: return
    val remainingOptions = options.filterNot { it.key == selectedOption.key }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.82f)
                    .heightIn(max = 620.dp),
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFFF8FAFC)
            ) {
                LazyColumn(modifier = Modifier.padding(vertical = 12.dp)) {
                    item {
                        Text(
                            text = "App language",
                            color = Color(0xFF111827),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                        )
                        HorizontalDivider(color = Color(0xFFE5E7EB))
                        SectionLabel(text = "Current language")
                    }

                    item {
                        LanguageOptionRow(
                            option = selectedOption,
                            selected = true,
                            onClick = { onOptionSelected(selectedOption.key) }
                        )
                    }

                    item {
                        SectionLabel(text = "All languages")
                    }

                    items(remainingOptions, key = LanguageOption::key) { option ->
                        LanguageOptionRow(
                            option = option,
                            selected = false,
                            onClick = { onOptionSelected(option.key) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        color = Color(0xFF374151),
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
    )
}

@Composable
private fun LanguageOptionRow(
    option: LanguageOption,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF3EA6FF),
                unselectedColor = Color(0xFF9CA3AF)
            )
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = option.label,
                color = Color(0xFF111827),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = option.nativeLabel,
                color = Color(0xFF4B5563),
                style = MaterialTheme.typography.bodyLarge
            )
            option.note?.let { note ->
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = note,
                    color = Color(0xFF6B7280),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
