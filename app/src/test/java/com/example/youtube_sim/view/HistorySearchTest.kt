package com.example.youtube_sim.view

import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.FeedItemType
import com.example.youtube_sim.model.HistoryEntry
import com.example.youtube_sim.model.HistorySection
import org.junit.Assert.assertEquals
import org.junit.Test

class HistorySearchTest {

    @Test
    fun filterHistorySections_matchesQueryAcrossTitleAndCreator() {
        val sections = listOf(
            HistorySection(
                title = "Today",
                entries = listOf(
                    HistoryEntry("shorts-unboxing-mac-desktop"),
                    HistoryEntry("apple-macbook-neo")
                )
            ),
            HistorySection(
                title = "Yesterday",
                entries = listOf(
                    HistoryEntry("music-jay-chou-i-do")
                )
            )
        )
        val itemsById = mapOf(
            "shorts-unboxing-mac-desktop" to feedItem(
                id = "shorts-unboxing-mac-desktop",
                title = "Unboxing a Mac Desktop",
                creator = "Apple Desk"
            ),
            "apple-macbook-neo" to feedItem(
                id = "apple-macbook-neo",
                title = "How Apple Designed MacBook Neo",
                creator = "Apple Design"
            ),
            "music-jay-chou-i-do" to feedItem(
                id = "music-jay-chou-i-do",
                title = "I Do",
                creator = "Jay Chou"
            )
        )

        val result = filterHistorySections(
            sections = sections,
            itemsById = itemsById,
            query = "apple mac",
            filter = HistoryFilter.ALL
        )

        assertEquals(listOf("Today"), result.map(HistorySection::title))
        assertEquals(
            listOf("shorts-unboxing-mac-desktop", "apple-macbook-neo"),
            result.flatMap { section -> section.entries.map(HistoryEntry::itemId) }
        )
    }

    @Test
    fun filterHistorySections_appliesShortsAndMusicFilters() {
        val sections = listOf(
            HistorySection(
                title = "Today",
                entries = listOf(
                    HistoryEntry("shorts-unboxing-mac-desktop"),
                    HistoryEntry("apple-macbook-neo"),
                    HistoryEntry("music-jay-chou-i-do")
                )
            )
        )
        val itemsById = mapOf(
            "shorts-unboxing-mac-desktop" to feedItem(id = "shorts-unboxing-mac-desktop"),
            "apple-macbook-neo" to feedItem(id = "apple-macbook-neo"),
            "music-jay-chou-i-do" to feedItem(id = "music-jay-chou-i-do")
        )

        val shortsResult = filterHistorySections(
            sections = sections,
            itemsById = itemsById,
            query = "",
            filter = HistoryFilter.SHORTS
        )
        val musicResult = filterHistorySections(
            sections = sections,
            itemsById = itemsById,
            query = "",
            filter = HistoryFilter.MUSIC
        )

        assertEquals(
            listOf("shorts-unboxing-mac-desktop"),
            shortsResult.flatMap { section -> section.entries.map(HistoryEntry::itemId) }
        )
        assertEquals(
            listOf("music-jay-chou-i-do"),
            musicResult.flatMap { section -> section.entries.map(HistoryEntry::itemId) }
        )
    }

    @Test
    fun filterHistorySections_canMatchEntryNotes() {
        val sections = listOf(
            HistorySection(
                title = "Today",
                entries = listOf(
                    HistoryEntry("apple-macbook-neo"),
                    HistoryEntry("all-screenshot-to-code", note = "Removed from watch history")
                )
            )
        )
        val itemsById = mapOf(
            "apple-macbook-neo" to feedItem(id = "apple-macbook-neo"),
            "all-screenshot-to-code" to feedItem(
                id = "all-screenshot-to-code",
                title = "Screenshot to Code",
                creator = "AI Product Lab"
            )
        )

        val result = filterHistorySections(
            sections = sections,
            itemsById = itemsById,
            query = "removed",
            filter = HistoryFilter.ALL
        )

        assertEquals(
            listOf("all-screenshot-to-code"),
            result.flatMap { section -> section.entries.map(HistoryEntry::itemId) }
        )
    }

    private fun feedItem(
        id: String,
        title: String = "How Apple Designed MacBook Neo",
        creator: String = "Apple Design"
    ) = FeedItem(
        id = id,
        type = FeedItemType.VIDEO,
        title = title,
        creator = creator,
        metadata = "212K views - 1 week ago",
        supportingText = null,
        actionText = "3:47",
        badgeText = null,
        sectionTitle = null,
        thumbnailLabel = "Thumbnail",
        assetPath = "data/video/sample.mp4",
        imagePath = null,
        accentStart = "#000000",
        accentEnd = "#FFFFFF"
    )
}
