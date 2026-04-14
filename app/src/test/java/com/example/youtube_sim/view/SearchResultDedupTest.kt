package com.example.youtube_sim.view

import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.FeedItemType
import com.example.youtube_sim.model.HomeTabContent
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchResultDedupTest {

    @Test
    fun buildSearchResults_replacesAllTabDuplicatesWithSpecificTabItems() {
        val sharedAsset = "data/video/macbook.mp4"
        val tabs = listOf(
            HomeTabContent(
                key = "all",
                label = "All",
                items = listOf(
                    feedItem(id = "all-macbook-neo", assetPath = sharedAsset),
                    feedItem(id = "all-itx", assetPath = "data/video/itx.mp4", title = "Build ITX")
                )
            ),
            HomeTabContent(
                key = "apple",
                label = "Apple",
                items = listOf(
                    feedItem(id = "apple-macbook-neo", assetPath = sharedAsset)
                )
            )
        )

        val resultIds = buildSearchResults(tabs).map(FeedItem::id)

        assertEquals(listOf("apple-macbook-neo", "all-itx"), resultIds)
    }

    @Test
    fun buildSearchResults_keepsDistinctItemsWhenAssetsDiffer() {
        val tabs = listOf(
            HomeTabContent(
                key = "all",
                label = "All",
                items = listOf(
                    feedItem(id = "all-macbook-neo", assetPath = "data/video/macbook.mp4"),
                    feedItem(id = "all-itx", assetPath = "data/video/itx.mp4", title = "Build ITX")
                )
            ),
            HomeTabContent(
                key = "shorts",
                label = "Shorts",
                items = listOf(
                    feedItem(
                        id = "shorts-mac",
                        assetPath = "data/video/short-mac.mp4",
                        title = "Mac Desk Short"
                    )
                )
            )
        )

        val resultIds = buildSearchResults(tabs).map(FeedItem::id)

        assertEquals(listOf("all-macbook-neo", "all-itx", "shorts-mac"), resultIds)
    }

    @Test
    fun filterSearchResults_matchesJayChouAliasesInChinese() {
        val results = listOf(
            feedItem(
                id = "music-jay-chou-nocturne",
                assetPath = "data/video/nocturne.mp4",
                title = "周杰伦 Jay Chou 夜曲",
                creator = "Jay Chou 周杰伦"
            ),
            feedItem(
                id = "music-blue-porcelain",
                assetPath = "data/video/blue-porcelain.mp4",
                title = "周杰伦 Jay Chou 青花瓷",
                creator = "Jay Chou 周杰伦"
            ),
            feedItem(
                id = "all-macbook-neo",
                assetPath = "data/video/macbook.mp4",
                title = "How Apple Designed MacBook Neo"
            )
        )

        val resultIds = filterSearchResults(results, "周杰伦").map(FeedItem::id)

        assertEquals(listOf("music-jay-chou-nocturne", "music-blue-porcelain"), resultIds)
    }

    @Test
    fun filterSearchResults_supportsFuzzyJayChouSongQueries() {
        val results = listOf(
            feedItem(
                id = "music-jay-chou-nocturne",
                assetPath = "data/video/nocturne.mp4",
                title = "周杰伦 Jay Chou 夜曲",
                creator = "Jay Chou 周杰伦"
            ),
            feedItem(
                id = "music-blue-porcelain",
                assetPath = "data/video/blue-porcelain.mp4",
                title = "周杰伦 Jay Chou 青花瓷",
                creator = "Jay Chou 周杰伦"
            )
        )

        val bluePorcelainIds = filterSearchResults(results, "周杰伦的青花瓷").map(FeedItem::id)
        val nocturneIds = filterSearchResults(results, "周杰伦的夜曲这首歌").map(FeedItem::id)

        assertEquals(listOf("music-blue-porcelain"), bluePorcelainIds)
        assertEquals(listOf("music-jay-chou-nocturne"), nocturneIds)
    }

    @Test
    fun filterSearchResults_matchesPossessiveJayChouSongQueries() {
        val results = listOf(
            feedItem(
                id = "music-blue-porcelain",
                assetPath = "data/video/blue-porcelain.mp4",
                title = "Jay Chou 青花瓷",
                creator = "Jay Chou"
            ),
            feedItem(
                id = "music-jay-chou-nocturne",
                assetPath = "data/video/nocturne.mp4",
                title = "Jay Chou 夜曲",
                creator = "Jay Chou"
            )
        )

        val bluePorcelainIds = filterSearchResults(results, "JayChou's青花瓷").map(FeedItem::id)
        val nocturneIds = filterSearchResults(results, "Jay Chou's 夜曲").map(FeedItem::id)

        assertEquals(listOf("music-blue-porcelain"), bluePorcelainIds)
        assertEquals(listOf("music-jay-chou-nocturne"), nocturneIds)
    }

    @Test
    fun filterSearchResults_doesNotMatchSingleLetterTokensInsideLongerQueries() {
        val results = listOf(
            feedItem(
                id = "music-jay-chou-i-do",
                assetPath = "data/video/i-do.mp4",
                title = "Jay Chou I Do",
                creator = "Jay Chou"
            ),
            feedItem(
                id = "apple-esim-travel",
                assetPath = "data/video/iphone.mp4",
                title = "How to use eSIM while traveling with your iPhone",
                creator = "Apple Support"
            )
        )

        val resultIds = filterSearchResults(results, "iphone").map(FeedItem::id)

        assertEquals(listOf("apple-esim-travel"), resultIds)
    }

    private fun feedItem(
        id: String,
        assetPath: String,
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
        assetPath = assetPath,
        imagePath = null,
        accentStart = "#000000",
        accentEnd = "#FFFFFF"
    )
}
