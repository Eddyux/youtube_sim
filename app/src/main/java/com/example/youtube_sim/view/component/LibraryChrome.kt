package com.example.youtube_sim.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
        Surface(
            modifier = Modifier.clickable(onClick = onBack),
            shape = CircleShape,
            color = Color(0xFFF4F4F5)
        ) {
            Icon(
                imageVector = BackIcon,
                contentDescription = "Back",
                modifier = Modifier.padding(8.dp).size(20.dp),
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        HeaderPill(CastIcon, "Cast") { onPlaceholderRequested("Cast", "Cast is reserved as a placeholder entry.") }
        Spacer(modifier = Modifier.width(8.dp))
        HeaderPill(SearchIcon, "Search") { onPlaceholderRequested("Search", "Search is reserved as a placeholder entry.") }
        Spacer(modifier = Modifier.width(8.dp))
        HeaderPill(MoreIcon, "More") { onPlaceholderRequested("More", "More is reserved as a placeholder entry.") }
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
private fun HeaderPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        shape = CircleShape,
        color = Color(0xFFF4F4F5)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.padding(8.dp).size(18.dp),
            tint = Color(0xFF111827)
        )
    }
}
