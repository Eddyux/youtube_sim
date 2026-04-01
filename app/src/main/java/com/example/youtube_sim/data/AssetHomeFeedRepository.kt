package com.example.youtube_sim.data

import android.content.Context
import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.FeedItemType
import com.example.youtube_sim.model.HomeTabContent
import org.json.JSONArray
import org.json.JSONObject

class AssetHomeFeedRepository(
    private val context: Context
) : HomeFeedRepository {

    override fun loadTabs(): List<HomeTabContent> {
        val raw = context.assets.open("data/home_feed.json").bufferedReader().use { it.readText() }
        val root = JSONObject(raw)
        val tabs = root.getJSONArray("tabs")
        return tabs.toHomeTabList()
    }

    private fun JSONArray.toHomeTabList(): List<HomeTabContent> {
        return List(length()) { index ->
            val item = getJSONObject(index)
            HomeTabContent(
                key = item.getString("key"),
                label = item.getString("label"),
                items = item.getJSONArray("items").toFeedItemList()
            )
        }
    }

    private fun JSONArray.toFeedItemList(): List<FeedItem> {
        return List(length()) { index ->
            getJSONObject(index).toFeedItem()
        }
    }

    private fun JSONObject.toFeedItem(): FeedItem {
        return FeedItem(
            id = getString("id"),
            type = FeedItemType.valueOf(getString("type").uppercase()),
            title = getString("title"),
            creator = getString("creator"),
            metadata = getString("metadata"),
            supportingText = optString("supportingText").ifBlank { null },
            actionText = optString("actionText").ifBlank { null },
            badgeText = optString("badgeText").ifBlank { null },
            sectionTitle = optString("sectionTitle").ifBlank { null },
            thumbnailLabel = getString("thumbnailLabel"),
            accentStart = getString("accentStart"),
            accentEnd = getString("accentEnd")
        )
    }
}
