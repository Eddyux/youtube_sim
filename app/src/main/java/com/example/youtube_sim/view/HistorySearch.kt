package com.example.youtube_sim.view

import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.HistoryEntry
import com.example.youtube_sim.model.HistorySection

internal enum class HistoryFilter(val label: String) {
    ALL("All"),
    VIDEOS("Videos"),
    SHORTS("Shorts"),
    MUSIC("Music")
}

internal fun filterHistorySections(
    sections: List<HistorySection>,
    itemsById: Map<String, FeedItem>,
    query: String,
    filter: HistoryFilter
): List<HistorySection> {
    val keywords = extractHistoryKeywords(query)

    return sections.mapNotNull { section ->
        val filteredEntries = section.entries.filter { entry ->
            val item = itemsById[entry.itemId] ?: return@filter false
            matchesHistoryFilter(item, filter) && matchesHistorySearch(item, entry, keywords)
        }

        if (filteredEntries.isEmpty()) {
            null
        } else {
            section.copy(entries = filteredEntries)
        }
    }
}

private fun matchesHistoryFilter(
    item: FeedItem,
    filter: HistoryFilter
): Boolean {
    return when (filter) {
        HistoryFilter.ALL -> true
        HistoryFilter.VIDEOS -> !item.id.startsWith("shorts-") && !item.id.startsWith("music-")
        HistoryFilter.SHORTS -> item.id.startsWith("shorts-")
        HistoryFilter.MUSIC -> item.id.startsWith("music-")
    }
}

private fun matchesHistorySearch(
    item: FeedItem,
    entry: HistoryEntry,
    keywords: List<String>
): Boolean {
    if (keywords.isEmpty()) {
        return true
    }

    val haystacks = listOf(
        item.title,
        item.creator,
        item.metadata,
        item.id,
        entry.note.orEmpty()
    ).map(::normalizeHistorySearchText)

    return keywords.all { keyword ->
        haystacks.any { haystack -> haystack.contains(keyword) }
    }
}

private fun extractHistoryKeywords(query: String): List<String> {
    return Regex("[\\p{L}\\p{N}]+")
        .findAll(normalizeHistorySearchText(query))
        .map { it.value }
        .toList()
}

private fun normalizeHistorySearchText(value: String): String {
    return value.lowercase()
        .replace("jay chou's", "jaychou ")
        .replace("jaychou's", "jaychou ")
        .replace("jay chou’s", "jaychou ")
        .replace("jaychou’s", "jaychou ")
        .replace("'s", " ")
        .replace("’s", " ")
        .replace("'", "")
        .replace("’", "")
        .replace("jay chou", "jaychou")
        .trim()
}
