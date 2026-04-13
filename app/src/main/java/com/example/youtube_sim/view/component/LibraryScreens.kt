package com.example.youtube_sim.view.component

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.HistorySection
import com.example.youtube_sim.model.OverflowMenuAction
import com.example.youtube_sim.model.PlaylistDetail
import com.example.youtube_sim.model.RootTab
import com.example.youtube_sim.view.HistoryFilter
import com.example.youtube_sim.view.filterHistorySections
import com.example.youtube_sim.view.resolveHistorySearchState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

@Composable
fun HistoryScreen(
    sections: List<HistorySection>,
    itemsById: Map<String, FeedItem>,
    overflowActions: List<OverflowMenuAction>,
    onFeedItemSelected: (String) -> Unit,
    onOverflowAction: (String, String) -> Unit,
    onBack: () -> Unit,
    onBottomTabSelected: (RootTab) -> Unit,
    onSearchRequested: () -> Unit
) {
    var menuItemId by remember { mutableStateOf<String?>(null) }
    var searchDraft by rememberSaveable { mutableStateOf("") }
    var submittedSearchQuery by rememberSaveable { mutableStateOf("") }
    var selectedFilter by rememberSaveable { mutableStateOf(HistoryFilter.ALL) }
    val searchState = remember(searchDraft, submittedSearchQuery) {
        resolveHistorySearchState(
            draftQuery = searchDraft,
            submittedQuery = submittedSearchQuery
        )
    }
    val filteredSections = remember(sections, itemsById, searchState.appliedQuery, selectedFilter) {
        filterHistorySections(
            sections = sections,
            itemsById = itemsById,
            query = searchState.appliedQuery,
            filter = selectedFilter
        )
    }
    val visibleItemCount = remember(filteredSections) {
        filteredSections.sumOf { it.entries.size }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LibraryScaffold(onBottomTabSelected = onBottomTabSelected) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                item {
                    LibraryTopBar(
                        title = "History",
                        onBack = onBack,
                        onSearchRequested = onSearchRequested,
                        onMoreRequested = {}
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    HistorySearchBar(
                        query = searchDraft,
                        onQueryChanged = { searchDraft = it },
                        onSearchSubmitted = {
                            submittedSearchQuery = searchDraft.trim()
                        },
                        onClear = {
                            searchDraft = ""
                            submittedSearchQuery = ""
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FilterChips(
                        labels = HistoryFilter.entries.map { it.label },
                        selectedLabel = selectedFilter.label,
                        onSelected = { label ->
                            selectedFilter = HistoryFilter.entries.firstOrNull { it.label == label } ?: HistoryFilter.ALL
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    if (searchState.hasPendingSearch) {
                        Text(
                            text = "Tap the search button to show results.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF6B7280)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    } else if (searchState.hasSubmittedSearch) {
                        Text(
                            text = "$visibleItemCount result(s)",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color(0xFF6B7280)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                if (filteredSections.isEmpty()) {
                    item {
                        EmptyHistoryState(
                            query = searchState.appliedQuery,
                            filter = selectedFilter
                        )
                    }
                } else {
                    filteredSections.forEach { section ->
                        item {
                            Text(text = section.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        items(section.entries, key = { "${section.title}-${it.itemId}" }) { entry ->
                            val item = itemsById[entry.itemId] ?: return@items
                            LibraryVideoRow(
                                item = item,
                                note = entry.note,
                                onClick = { onFeedItemSelected(item.id) },
                                onMoreClick = { menuItemId = item.id }
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                        }
                    }
                }
            }
        }

        if (menuItemId != null) {
            OverflowActionsSheet(
                actions = overflowActions,
                onActionSelected = { actionKey ->
                    val itemId = menuItemId ?: return@OverflowActionsSheet
                    onOverflowAction(itemId, actionKey)
                    menuItemId = null
                },
                onDismiss = { menuItemId = null }
            )
        }
    }
}

@Composable
private fun HistorySearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearchSubmitted: () -> Unit,
    onClear: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Search watch history") },
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        leadingIcon = {
            Icon(
                imageVector = SearchIcon,
                contentDescription = "Search",
                tint = Color(0xFF6B7280)
            )
        },
        trailingIcon = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (query.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Clear",
                        tint = Color(0xFF6B7280),
                        modifier = Modifier.clickable(onClick = onClear)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Icon(
                    imageVector = SearchIcon,
                    contentDescription = "Search",
                    tint = Color(0xFF6B7280),
                    modifier = Modifier.clickable(onClick = onSearchSubmitted)
                )
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearchSubmitted() }),
        colors = androidx.compose.material3.TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF4F4F5),
            unfocusedContainerColor = Color(0xFFF4F4F5),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun EmptyHistoryState(
    query: String,
    filter: HistoryFilter
) {
    val message = when {
        query.isNotBlank() -> "No history matches \"$query\""
        filter != HistoryFilter.ALL -> "No ${filter.label.lowercase()} history yet"
        else -> "No watch history yet"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 56.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(shape = RoundedCornerShape(18.dp), color = Color(0xFFF4F4F5)) {
                Icon(
                    imageVector = SearchIcon,
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp).size(28.dp),
                    tint = Color(0xFF9CA3AF)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = message, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Try another keyword or switch the history filter.",
                color = Color(0xFF6B7280),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun PlaylistScreen(
    playlist: PlaylistDetail,
    itemsById: Map<String, FeedItem>,
    overflowActions: List<OverflowMenuAction>,
    onFeedItemSelected: (String) -> Unit,
    onOverflowAction: (String, String, String) -> Unit,
    onBack: () -> Unit,
    onBottomTabSelected: (RootTab) -> Unit,
    onSearchRequested: () -> Unit
) {
    var menuItemId by remember { mutableStateOf<String?>(null) }
    val removeAction = OverflowMenuAction(
        key = "remove_playlist",
        label = if (playlist.key == "liked_videos") "Remove from Liked videos" else "Remove from Watch later"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LibraryScaffold(onBottomTabSelected = onBottomTabSelected) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                item {
                    LibraryTopBar(
                        title = "",
                        onBack = onBack,
                        onSearchRequested = onSearchRequested,
                        onMoreRequested = {}
                    )
                    Spacer(modifier = Modifier.height(0.dp))
                    PlaylistHeader(playlist = playlist, itemsById = itemsById)
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), color = Color.Black) {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                            text = "Play all",
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(playlist.itemIds, key = { "${playlist.key}-$it" }) { itemId ->
                    val item = itemsById[itemId] ?: return@items
                    LibraryVideoRow(
                        item = item,
                        onClick = { onFeedItemSelected(item.id) },
                        onMoreClick = { menuItemId = item.id }
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }
        }

        if (menuItemId != null) {
            OverflowActionsSheet(
                actions = overflowActions + removeAction,
                onActionSelected = { actionKey ->
                    val itemId = menuItemId ?: return@OverflowActionsSheet
                    onOverflowAction(playlist.key, itemId, actionKey)
                    menuItemId = null
                },
                onDismiss = { menuItemId = null }
            )
        }
    }
}

@Composable
private fun PlaylistHeader(playlist: PlaylistDetail, itemsById: Map<String, FeedItem>) {
    val firstItem = itemsById[playlist.itemIds.firstOrNull()]
    val colors = listOf(firstItem?.accentStart?.toScreenColor() ?: Color(0xFF27272A), firstItem?.accentEnd?.toScreenColor() ?: Color(0xFF52525B))
    Column {
        if (firstItem != null) {
            AssetThumbnail(item = firstItem, modifier = Modifier.fillMaxWidth().aspectRatio(1.25f)) {
                Text(modifier = Modifier.align(Alignment.BottomStart).padding(18.dp), text = playlist.title, color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            }
        } else {
            Box(
                modifier = Modifier.fillMaxWidth().aspectRatio(1.25f).background(Brush.linearGradient(colors), RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(modifier = Modifier.padding(18.dp), text = playlist.title, color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(text = playlist.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = playlist.metadata, color = Color(0xFF6B7280))
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = playlist.description, color = Color(0xFF6B7280))
    }
}

@Composable
private fun LibraryVideoRow(
    item: FeedItem,
    note: String? = null,
    onClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick), verticalAlignment = Alignment.Top) {
        AssetThumbnail(item = item, modifier = Modifier.width(156.dp).aspectRatio(16f / 9f)) {
            item.actionText?.let { duration ->
                Surface(modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp), shape = RoundedCornerShape(4.dp), color = Color(0xD9000000)) {
                    Text(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), text = duration, color = Color.White, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, style = MaterialTheme.typography.bodyLarge, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.creator, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
            Text(text = item.metadata, style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
            note?.let {
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = it, style = MaterialTheme.typography.bodySmall, color = Color(0xFF9CA3AF))
            }
        }
        Icon(
            imageVector = MoreIcon,
            contentDescription = "More",
            tint = Color(0xFF737373),
            modifier = Modifier.size(18.dp).clickable(onClick = onMoreClick)
        )
    }
}

private fun String.toScreenColor(): Color {
    return runCatching { Color(parseColor(this)) }.getOrDefault(Color(0xFF52525B))
}
