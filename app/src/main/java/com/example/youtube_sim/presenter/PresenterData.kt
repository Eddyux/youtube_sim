package com.example.youtube_sim.presenter

import com.example.youtube_sim.model.GeneralSettingsItem
import com.example.youtube_sim.model.HeaderAction
import com.example.youtube_sim.model.HistoryEntry
import com.example.youtube_sim.model.HistoryPreview
import com.example.youtube_sim.model.HistorySection
import com.example.youtube_sim.model.HomeChip
import com.example.youtube_sim.model.NotificationSettingsItem
import com.example.youtube_sim.model.PlaySettingsMenuItem
import com.example.youtube_sim.model.PlaylistDetail
import com.example.youtube_sim.model.PlaylistPreview
import com.example.youtube_sim.model.SettingsGroup
import com.example.youtube_sim.model.SettingsItem
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
    HeaderAction(key = "cast", emoji = "📺", label = "Cast"),
    HeaderAction(key = "notifications", emoji = "🔔", label = "Notifications"),
    HeaderAction(key = "search", emoji = "🔍", label = "Search")
)

internal val youEntries = listOf(
    YouMenuEntry(key = "history", label = "History", subtitle = "Recent watched videos", emoji = "🕘"),
    YouMenuEntry(key = "playlists", label = "Playlists", subtitle = "Saved collections and queues", emoji = "🎞"),
    YouMenuEntry(key = "settings", label = "Settings", subtitle = "Account and app preferences", emoji = "⚙"),
    YouMenuEntry(key = "your-videos", label = "Your videos", subtitle = "Creator tools entry", emoji = "🎬")
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
    HistoryPreview("Space launches this week", "#0F172A", "#2563EB"),
    HistoryPreview("Studio music sessions", "#312E81", "#9333EA"),
    HistoryPreview("Apple hardware round-up", "#374151", "#9CA3AF")
)

internal val historySections = listOf(
    HistorySection(
        title = "Today",
        entries = listOf(
            HistoryEntry("video-backrooms"),
            HistoryEntry("apple-m4-ssd"),
            HistoryEntry("video-car-review", note = "Removed from watch history")
        )
    ),
    HistorySection(
        title = "Yesterday",
        entries = listOf(
            HistoryEntry("apple-halftime"),
            HistoryEntry("music-sunflower")
        )
    ),
    HistorySection(
        title = "Wednesday",
        entries = listOf(
            HistoryEntry("apple-mac-vs-pc"),
            HistoryEntry("apple-iphone17-battery")
        )
    ),
    HistorySection(
        title = "Oct 7, 2024",
        entries = listOf(
            HistoryEntry("music-wake-me-up"),
            HistoryEntry("video-merry-christmas")
        )
    )
)

internal val playlistPreviews = listOf(
    PlaylistPreview(key = "watch_later", title = "Watch later", privacy = "Private", count = "2"),
    PlaylistPreview(key = "liked_videos", title = "Liked videos", privacy = "Private", count = "2")
)

internal val playlistDetails = listOf(
    PlaylistDetail(
        key = "watch_later",
        title = "Watch later",
        metadata = "Playlist · Private · 2 videos · No views",
        description = "Short queue for videos you plan to revisit later.",
        itemIds = listOf("apple-macbook-experience", "video-car-review")
    ),
    PlaylistDetail(
        key = "liked_videos",
        title = "Liked videos",
        metadata = "Playlist · Private · 2 videos · No views",
        description = "Videos you have already marked with a like.",
        itemIds = listOf("video-merry-christmas", "video-backrooms")
    )
)

internal val settingsGroups = listOf(
    SettingsGroup(
        title = "Account",
        items = listOf(
            SettingsItem("General", "⚙"),
            SettingsItem("Switch or manage account", "👤"),
            SettingsItem("Family Center", "👨‍👩‍👧"),
            SettingsItem("Languages", "🌐"),
            SettingsItem("Time management", "⏱"),
            SettingsItem("Notifications", "🔔"),
            SettingsItem("Purchases and memberships", "💳"),
            SettingsItem("Billing & payments", "💰"),
            SettingsItem("Manage all history", "🕘"),
            SettingsItem("Your data in YouTube", "🧾"),
            SettingsItem("Privacy", "🔒"),
            SettingsItem("Connected apps", "🔌"),
            SettingsItem("Try experimental new features", "🧪")
        )
    ),
    SettingsGroup(
        title = "Video and audio preferences",
        items = listOf(
            SettingsItem("Quality", "▶"),
            SettingsItem("Playback", "⏯"),
            SettingsItem("Captions", "💬"),
            SettingsItem("Data saving", "📶"),
            SettingsItem("Accessibility", "♿"),
            SettingsItem("Watch on TV", "📺")
        )
    ),
    SettingsGroup(
        title = "Help and policy",
        items = listOf(
            SettingsItem("Help", "❓"),
            SettingsItem("YouTube Terms of Service", "📄"),
            SettingsItem("Send feedback", "📝"),
            SettingsItem("About", "ℹ")
        )
    )
)

internal val generalSettings = listOf(
    GeneralSettingsItem("appearance", "Appearance", subtitle = "Use device theme"),
    GeneralSettingsItem("rotate_shorts", "Rotate Shorts", hasToggle = true),
    GeneralSettingsItem("playback_in_feeds", "Playback in feeds", subtitle = "Wi-Fi only"),
    GeneralSettingsItem("voice_search_language", "Voice search language", subtitle = "English (United States)"),
    GeneralSettingsItem("location", "Location", subtitle = "United States"),
    GeneralSettingsItem(
        "restricted_mode",
        "Restricted Mode",
        subtitle = "This helps hide potentially mature videos. No filter is fully accurate.",
        hasToggle = true
    ),
    GeneralSettingsItem("stats_for_nerds", "Enable stats for nerds", hasToggle = true),
    GeneralSettingsItem(
        "earn_badges",
        "Earn badges",
        subtitle = "Badges are awarded for viewer milestones.",
        hasToggle = true
    )
)

internal val notificationSettings = listOf(
    NotificationSettingsItem(
        "scheduled_digest",
        "Scheduled digest",
        description = "Combine notifications into one summary each day",
        hasToggle = true
    ),
    NotificationSettingsItem(
        "subscriptions",
        "Subscriptions",
        description = "Notify me about activity from subscribed channels",
        hasToggle = true
    ),
    NotificationSettingsItem(
        "channel_settings",
        "Channel settings",
        description = "Manage notifications for specific channels"
    ),
    NotificationSettingsItem(
        "recommended_videos",
        "Recommended videos",
        description = "Notify me about videos I might like",
        hasToggle = true
    ),
    NotificationSettingsItem(
        "product_updates",
        "Product updates",
        description = "Updates about YouTube features and tips",
        hasToggle = true
    ),
    NotificationSettingsItem(
        "watch_on_tv",
        "Watch on TV",
        description = "Notify me about activity on connected TVs",
        hasToggle = true
    )
)

internal val defaultToggles = mapOf(
    "rotate_shorts" to false,
    "restricted_mode" to false,
    "stats_for_nerds" to false,
    "earn_badges" to true,
    "scheduled_digest" to false,
    "subscriptions" to true,
    "recommended_videos" to true,
    "product_updates" to true,
    "watch_on_tv" to true,
    "loop_video" to false,
    "ambient_mode" to true,
    "stable_volume" to false
)

internal val playSettingsItems = listOf(
    PlaySettingsMenuItem("quality", "Quality", "⚙", currentValue = "Auto (720p)"),
    PlaySettingsMenuItem("playback_speed", "Playback speed", "⏱", currentValue = "1x"),
    PlaySettingsMenuItem("captions", "Captions", "💬", currentValue = "Chinese"),
    PlaySettingsMenuItem("lock_screen", "Lock screen", "🔒"),
    PlaySettingsMenuItem("more", "More", "⋯")
)

internal val playSettingsMoreItems = listOf(
    PlaySettingsMenuItem("loop_video", "Loop video", "🔁", hasToggle = true),
    PlaySettingsMenuItem("ambient_mode", "Ambient mode", "✨", hasToggle = true),
    PlaySettingsMenuItem("stable_volume", "Stable volume", "🔊", hasToggle = true),
    PlaySettingsMenuItem("sleep_timer", "Sleep timer", "⏰", currentValue = "Off"),
    PlaySettingsMenuItem("watch_in_vr", "Watch in VR", "🥽"),
    PlaySettingsMenuItem("help_feedback", "Help & feedback", "❓")
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
