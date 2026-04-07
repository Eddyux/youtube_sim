package com.example.youtube_sim.presenter

import com.example.youtube_sim.model.HeaderAction
import com.example.youtube_sim.model.ChannelProfile
import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.HistoryEntry
import com.example.youtube_sim.model.HistoryPreview
import com.example.youtube_sim.model.HistorySection
import com.example.youtube_sim.model.HomeChip
import com.example.youtube_sim.model.HomeTabContent
import com.example.youtube_sim.model.OverflowMenuAction
import com.example.youtube_sim.model.PlaylistDetail
import com.example.youtube_sim.model.PlaylistPreview
import com.example.youtube_sim.model.SubscriptionChannel
import com.example.youtube_sim.model.SubscriptionGroup
import com.example.youtube_sim.model.VideoComment
import com.example.youtube_sim.model.YouMenuEntry

internal val homeChips = listOf(
    HomeChip(key = "all", label = "All", implemented = true),
    HomeChip(key = "podcasts", label = "Podcasts", implemented = false),
    HomeChip(key = "music", label = "Music", implemented = true),
    HomeChip(key = "apple", label = "Apple", implemented = true),
    HomeChip(key = "live", label = "Live", implemented = true)
)

internal val headerActions = listOf(
    HeaderAction(key = "cast", emoji = "Cast", label = "Cast"),
    HeaderAction(key = "notifications", emoji = "Bell", label = "Notifications"),
    HeaderAction(key = "search", emoji = "Find", label = "Search")
)

internal val youEntries = listOf(
    YouMenuEntry(key = "history", label = "History", subtitle = "Recent watched videos", emoji = "His"),
    YouMenuEntry(key = "playlists", label = "Playlists", subtitle = "Saved collections and queues", emoji = "List"),
    YouMenuEntry(key = "settings", label = "Settings", subtitle = "Account and app preferences", emoji = "Set"),
    YouMenuEntry(key = "your-videos", label = "Your videos", subtitle = "Creator tools entry", emoji = "Vid"),
    YouMenuEntry(key = "movies", label = "Movies", subtitle = "Purchases and rentals", emoji = "Mov")
)

internal val subscriptionGroups = listOf(
    SubscriptionGroup(
        title = "Beauty & Fashion",
        channels = listOf(
            SubscriptionChannel("Jeffreestar", true),
            SubscriptionChannel("Courtney W", false),
            SubscriptionChannel("Miaaaw Love", false)
        )
    ),
    SubscriptionGroup(
        title = "Comedy",
        channels = listOf(
            SubscriptionChannel("Dropout", true),
            SubscriptionChannel("A.I. Games", false),
            SubscriptionChannel("Alan Chikin", true)
        )
    ),
    SubscriptionGroup(
        title = "Music",
        channels = listOf(
            SubscriptionChannel("Jay Chou", true),
            SubscriptionChannel("Neko Music", false),
            SubscriptionChannel("Post Malone", true)
        )
    ),
    SubscriptionGroup(
        title = "Tech",
        channels = listOf(
            SubscriptionChannel("MKBHD", true),
            SubscriptionChannel("Tech YES City", true),
            SubscriptionChannel("Hardware Canucks", true)
        )
    )
)

internal val historyPreviews = listOf(
    HistoryPreview("DIY PC builds", "#0F172A", "#2563EB"),
    HistoryPreview("Jay Chou replay", "#312E81", "#9333EA"),
    HistoryPreview("Apple quick clips", "#374151", "#9CA3AF")
)

internal val historySections = listOf(
    HistorySection(
        title = "Today",
        entries = listOf(
            HistoryEntry("shorts-unboxing-mac-desktop"),
            HistoryEntry("apple-macbook-neo"),
            HistoryEntry("all-screenshot-to-code", note = "Removed from watch history")
        )
    ),
    HistorySection(
        title = "Yesterday",
        entries = listOf(
            HistoryEntry("music-jay-chou-i-do"),
            HistoryEntry("apple-airpods-pro-3")
        )
    ),
    HistorySection(
        title = "Wednesday",
        entries = listOf(
            HistoryEntry("shorts-overkill-mini-pc"),
            HistoryEntry("all-mini-lunchbox-pc")
        )
    ),
    HistorySection(
        title = "Oct 7, 2024",
        entries = listOf(
            HistoryEntry("music-blue-porcelain"),
            HistoryEntry("music-jay-chou-nocturne")
        )
    )
)

internal val playlistDetails = listOf(
    PlaylistDetail(
        key = "watch_later",
        title = "Watch later",
        metadata = "Playlist - Private - 3 videos - No views",
        description = "Saved videos you want to revisit from the new asset library.",
        itemIds = listOf("all-build-itx-too-late", "apple-esim-travel", "all-screenshot-to-code")
    ),
    PlaylistDetail(
        key = "liked_videos",
        title = "Liked videos",
        metadata = "Playlist - Private - 3 videos - No views",
        description = "Videos you have already marked with a like.",
        itemIds = listOf("music-taylor-ophelia", "shorts-unboxing-mac-desktop", "music-jay-chou-i-do")
    )
)

internal val historyOverflowActions = listOf(
    OverflowMenuAction("queue", "Play next in queue"),
    OverflowMenuAction("watch_later", "Save to Watch later"),
    OverflowMenuAction("playlist", "Save to playlist"),
    OverflowMenuAction("download", "Download video"),
    OverflowMenuAction("share", "Share"),
    OverflowMenuAction("remove_history", "Remove from watch history")
)

internal val playlistOverflowActions = listOf(
    OverflowMenuAction("queue", "Play next in queue"),
    OverflowMenuAction("watch_later", "Save to Watch later"),
    OverflowMenuAction("playlist", "Save to playlist"),
    OverflowMenuAction("download", "Download video"),
    OverflowMenuAction("share", "Share")
)

internal val comments = listOf(
    VideoComment(
        author = "TechFan42",
        handle = "@techfan42",
        text = "This is one of the clearest beginner breakdowns I have watched this month.",
        likes = "53",
        timeAgo = "2 days ago",
        replyCount = "4 replies"
    ),
    VideoComment(
        author = "AI Learner",
        handle = "@ai_learner",
        text = "Could you make a follow-up about prompt evaluation and tool calling?",
        likes = "21",
        timeAgo = "1 day ago"
    ),
    VideoComment(
        author = "CodeNewbie",
        handle = "@codenewbie",
        text = "Finally a tutorial that explains what the API pieces are doing instead of jumping straight to code.",
        likes = "15",
        timeAgo = "5 hours ago",
        replyCount = "1 reply"
    ),
    VideoComment(
        author = "StreamWatcher",
        handle = "@streamwatcher",
        text = "The examples around real app flows are the best part of this video.",
        likes = "8",
        timeAgo = "3 hours ago"
    )
)

internal fun buildPlaylistPreviews(details: List<PlaylistDetail>): List<PlaylistPreview> {
    return details.map { detail ->
        PlaylistPreview(
            key = detail.key,
            title = detail.title,
            privacy = "Private",
            count = detail.itemIds.size.toString()
        )
    }
}

internal fun buildChannelProfiles(tabs: List<HomeTabContent>): List<ChannelProfile> {
    val items = tabs.filter { it.key != "live" }.flatMap { it.items }
    val groups = items.groupBy { normalizeCreatorKey(it.creator) }
    return groups.mapNotNull { (key, videos) ->
        val primary = videos.firstOrNull() ?: return@mapNotNull null
        val title = preferredChannelTitle(key, primary.creator)
        val featured = preferredFeaturedItem(key, videos)
        val hero = preferredHeroItem(key, videos, featured)
        ChannelProfile(
            key = key,
            title = title,
            handle = "@${key.replace("_", "")}",
            subscribers = subscriberLabelFor(key),
            heroItemId = hero.id,
            featuredItemId = featured.id,
            videoItemIds = videos.distinctBy(FeedItem::id).map(FeedItem::id),
            description = channelDescriptionFor(key, title)
        )
    }.sortedBy(ChannelProfile::title)
}

private fun normalizeCreatorKey(creator: String): String {
    val lowercase = creator.lowercase()
    return when {
        "jay chou" in lowercase -> "jay_chou"
        else -> creator.lowercase().replace(Regex("[^a-z0-9]+"), "_").trim('_')
    }
}

private fun preferredChannelTitle(key: String, fallback: String): String {
    return when (key) {
        "jay_chou" -> "Jay Chou"
        else -> fallback
    }
}

private fun preferredHeroItem(key: String, videos: List<FeedItem>, featured: FeedItem): FeedItem {
    return when (key) {
        "jay_chou" -> videos.firstOrNull { it.id.contains("blue-porcelain") } ?: featured
        else -> videos.first()
    }
}

private fun preferredFeaturedItem(key: String, videos: List<FeedItem>): FeedItem {
    return when (key) {
        "jay_chou" -> videos.firstOrNull { it.id.contains("i-do") } ?: videos.first()
        else -> videos.first()
    }
}

private fun subscriberLabelFor(key: String): String {
    return when (key) {
        "jay_chou" -> "7.2M subscribers"
        "apple" -> "18.4M subscribers"
        "apple_design" -> "1.8M subscribers"
        "apple_support" -> "1.3M subscribers"
        "taylor_swift" -> "58.1M subscribers"
        "tech_yes_city" -> "685K subscribers"
        else -> "Local creator"
    }
}

private fun channelDescriptionFor(key: String, title: String): String {
    return when (key) {
        "jay_chou" -> "Mandopop classics, restored music videos, and signature cinematic releases."
        "apple" -> "Product launches and polished teaser videos from the local asset set."
        "apple_design" -> "Design-driven hardware stories and behind-the-scenes product breakdowns."
        "apple_support" -> "Practical iPhone walkthroughs and support-focused quick guides."
        "tech_yes_city" -> "PC building advice, benchmark talk, and compact desktop experiments."
        else -> "$title videos collected from the local library."
    }
}
