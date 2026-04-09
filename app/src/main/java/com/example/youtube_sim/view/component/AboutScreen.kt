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

@Composable
fun AboutScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.clickable(onClick = onBack),
                shape = androidx.compose.foundation.shape.CircleShape,
                color = Color.Transparent
            ) {
                Icon(
                    imageVector = BackIcon,
                    contentDescription = "Back",
                    modifier = Modifier.padding(4.dp).size(24.dp),
                    tint = Color(0xFF111827)
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF111827)
            )
        }

        Spacer(modifier = Modifier.height(34.dp))

        AboutEntry(
            title = "Google Privacy Policy",
            subtitle = "Read Mobile Privacy Policy"
        )
        Spacer(modifier = Modifier.height(28.dp))

        AboutEntry(
            title = "Open source licenses",
            subtitle = "License details for open source software"
        )
        Spacer(modifier = Modifier.height(28.dp))

        AboutEntry(
            title = "App version",
            subtitle = "21.08.265",
            trailing = {
                Icon(
                    imageVector = InfoIcon,
                    contentDescription = "Version info",
                    tint = Color(0xFF111827),
                    modifier = Modifier.size(20.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "YouTube, a Google company",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF3F3F46)
        )
    }
}

@Composable
private fun AboutEntry(
    title: String,
    subtitle: String,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF3F3F46)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF71717A)
            )
        }
        trailing?.invoke()
    }
}
