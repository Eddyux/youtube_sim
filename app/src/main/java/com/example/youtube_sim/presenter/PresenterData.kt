package com.example.youtube_sim.presenter

import com.example.youtube_sim.model.HeaderAction
import com.example.youtube_sim.model.HistoryEntry
import com.example.youtube_sim.model.HistoryPreview
import com.example.youtube_sim.model.HistorySection
import com.example.youtube_sim.model.HomeChip
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
            SubscriptionChannel("M2M", false),
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

internal val playlistPreviews = listOf(
    PlaylistPreview(key = "watch_later", title = "Watch later", privacy = "Private", count = "3"),
    PlaylistPreview(key = "liked_videos", title = "Liked videos", privacy = "Private", count = "3")
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
