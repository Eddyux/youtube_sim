package com.example.youtube_sim.view

import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.HomeTabContent

private data class SearchCandidate(
    val item: FeedItem,
    val priority: Int
)

internal fun buildSearchResults(tabs: List<HomeTabContent>): List<FeedItem> {
    val deduped = linkedMapOf<String, SearchCandidate>()

    tabs.asSequence()
        .filterNot { it.key == "live" }
        .forEach { tab ->
            tab.items.forEach { item ->
                val dedupKey = item.assetPath
                    ?.trim()
                    ?.lowercase()
                    ?.takeIf { it.isNotEmpty() }
                    ?: item.imagePath
                        ?.trim()
                        ?.lowercase()
                        ?.takeIf { it.isNotEmpty() }
                    ?: "${item.title.trim().lowercase()}|${item.creator.trim().lowercase()}"
                val candidate = SearchCandidate(
                    item = item,
                    priority = searchPriorityForTab(tab.key)
                )
                val current = deduped[dedupKey]
                if (current == null || candidate.priority > current.priority) {
                    deduped[dedupKey] = candidate
                }
            }
        }

    return deduped.values.map(SearchCandidate::item)
}

internal fun filterSearchResults(results: List<FeedItem>, query: String): List<FeedItem> {
    val queryKeywords = extractSearchKeywords(query)
    if (queryKeywords.isEmpty()) return emptyList()

    return results.filter { item ->
        itemMatchesQuery(item, queryKeywords)
    }
}

private fun searchPriorityForTab(tabKey: String): Int {
    return when (tabKey) {
        "all" -> 0
        else -> 1
    }
}

private fun searchTermsForItem(item: FeedItem): List<String> {
    val aliases = buildList {
        add(item.title)
        add(item.creator)
        add(item.id)

        if ("jay-chou" in item.id || item.creator.contains("Jay Chou", ignoreCase = true)) {
            add("Jay Chou")
            add("JayChou")
            add("Jay Chou's")
            add("JayChou's")
            add("周杰伦")
            add("周杰伦的歌")
        }
    }
    return aliases
}

private fun itemMatchesQuery(item: FeedItem, queryKeywords: List<String>): Boolean {
    val itemKeywords = searchTermsForItem(item)
        .flatMap(::extractSearchKeywords)
        .toSet()
    val joinedKeywords = itemKeywords.joinToString(separator = "")

    return queryKeywords.all { queryKeyword ->
        itemKeywords.any { keyword ->
            keyword.contains(queryKeyword)
        } || joinedKeywords.contains(queryKeyword)
    }
}

private fun extractSearchKeywords(value: String): List<String> {
    val normalized = normalizeSearchText(value)
        .replace("jay chou", "jaychou")
        .replace("周杰伦的", "周杰伦 ")
        .replace("的", " ")
        .replace("这首歌", " ")
        .replace("什么歌", " ")
        .replace("哪首歌", " ")
        .replace("歌曲", " ")
        .replace("音乐", " ")
        .replace("视频", " ")
        .replace("official", " ")
        .replace("music", " ")
        .replace("video", " ")
        .replace("mv", " ")
    return Regex("[\\p{IsHan}]+|[a-z0-9]+")
        .findAll(normalized)
        .map { it.value }
        .filterNot(::isSearchStopWord)
        .toList()
}

private fun normalizeSearchText(value: String): String {
    return value.lowercase()
        .replace("jay chou's", "jaychou ")
        .replace("jaychou's", "jaychou ")
        .replace("jay chou’s", "jaychou ")
        .replace("jaychou’s", "jaychou ")
        .replace("'s", " ")
        .replace("’s", " ")
        .replace("'", "")
        .replace("’", "")
        .replace("周杰倫", "周杰伦")
        .replace("jay chou", "jaychou")
}

private fun isSearchStopWord(value: String): Boolean {
    return value in setOf(
        "什么",
        "哪首",
        "这首",
        "歌"
    )
}
