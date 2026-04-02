package com.example.youtube_sim.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.RootTab

@Composable
fun LibraryScaffold(
    onBottomTabSelected: (RootTab) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(bottomBar = { BottomBar(currentTab = RootTab.YOU, onTabSelected = onBottomTabSelected) }) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
fun LibraryTopBar(
    title: String,
    onBack: () -> Unit,
    onPlaceholderRequested: (String, String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.clickable(onClick = onBack).padding(end = 12.dp),
            text = "←",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        HeaderPill("📺") { onPlaceholderRequested("Cast", "Cast is reserved as a placeholder entry.") }
        Spacer(modifier = Modifier.width(8.dp))
        HeaderPill("🔍") { onPlaceholderRequested("Search", "Search is reserved as a placeholder entry.") }
        Spacer(modifier = Modifier.width(8.dp))
        HeaderPill("⋮") { onPlaceholderRequested("More", "More is reserved as a placeholder entry.") }
    }
}

@Composable
fun FilterChips(labels: List<String>) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        labels.forEachIndexed { index, label ->
            Surface(shape = RoundedCornerShape(12.dp), color = if (index == 0) Color.Black else Color(0xFFF4F4F5)) {
                Text(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    text = label,
                    color = if (index == 0) Color.White else Color.Black
                )
            }
        }
    }
}

@Composable
private fun HeaderPill(label: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFF4F4F5)
    ) {
        Text(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp), text = label)
    }
}
