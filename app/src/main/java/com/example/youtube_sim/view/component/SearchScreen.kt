package com.example.youtube_sim.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.FeedItem

@Composable
fun SearchScreen(
    query: String,
    results: List<FeedItem>,
    onQueryChanged: (String) -> Unit,
    onFeedItemSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    val normalizedQuery = query.trim()
    val filtered = if (normalizedQuery.isBlank()) {
        emptyList()
    } else {
        results.filter { item ->
            item.title.contains(normalizedQuery, ignoreCase = true) ||
                item.creator.contains(normalizedQuery, ignoreCase = true)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp)
    ) {
        item {
            SearchTopBar(query = query, onQueryChanged = onQueryChanged, onBack = onBack)
            Spacer(modifier = Modifier.height(18.dp))
            if (normalizedQuery.isBlank()) {
                Text(
                    text = "Enter a keyword to search creators, songs, or product videos.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280)
                )
                Spacer(modifier = Modifier.height(18.dp))
            } else {
                Text(
                    text = "${filtered.size} result(s)",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFF6B7280)
                )
                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        if (filtered.isEmpty()) {
            item {
                EmptySearchState(query = normalizedQuery)
            }
        } else {
            items(filtered, key = FeedItem::id) { item ->
                SearchResultRow(item = item, onClick = { onFeedItemSelected(item.id) })
                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        item { Spacer(modifier = Modifier.height(28.dp)) }
    }
}

@Composable
private fun SearchTopBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onBack: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            modifier = Modifier.clickable(onClick = onBack),
            shape = CircleShape,
            color = Color(0xFFF4F4F5)
        ) {
            Icon(
                imageVector = BackIcon,
                contentDescription = "Back",
                modifier = Modifier.padding(10.dp).size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier.weight(1f),
            placeholder = { Text(text = "Search YouTube") },
            singleLine = true,
            shape = RoundedCornerShape(18.dp),
            trailingIcon = {
                Icon(
                    imageVector = SearchIcon,
                    contentDescription = "Search",
                    tint = Color(0xFF6B7280)
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {}),
            colors = androidx.compose.material3.TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color(0xFFE5E7EB),
                unfocusedIndicatorColor = Color(0xFFE5E7EB)
            )
        )
    }
}

@Composable
private fun SearchResultRow(
    item: FeedItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.Top
    ) {
        AssetThumbnail(
            item = item,
            modifier = Modifier
                .width(156.dp)
                .aspectRatio(16f / 9f)
        ) {
            item.actionText?.let { duration ->
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp),
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xD9000000)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        text = duration,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.creator, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
            Text(text = item.metadata, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
        }
    }
}

@Composable
private fun EmptySearchState(query: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 56.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(shape = CircleShape, color = Color(0xFFF4F4F5)) {
                Icon(
                    imageVector = SearchIcon,
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp).size(28.dp),
                    tint = Color(0xFF9CA3AF)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "No matches for \"$query\"", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Try another title or creator name from the local library.",
                color = Color(0xFF6B7280),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
