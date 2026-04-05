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

internal val settingsGroups = listOf(
    SettingsGroup(
        title = "Account",
        items = listOf(
            SettingsItem("General", "Gen"),
            SettingsItem("Switch or manage account", "Acct"),
            SettingsItem("Family Center", "Fam"),
            SettingsItem("Languages", "Lang"),
            SettingsItem("Time management", "Time"),
            SettingsItem("Notifications", "Bell"),
            SettingsItem("Purchases and memberships", "Shop"),
            SettingsItem("Billing & payments", "Pay"),
            SettingsItem("Manage all history", "Hist"),
            SettingsItem("Your data in YouTube", "Data"),
            SettingsItem("Privacy", "Lock"),
            SettingsItem("Connected apps", "Apps"),
            SettingsItem("Try experimental new features", "Lab")
        )
    ),
    SettingsGroup(
        title = "Video and audio preferences",
        items = listOf(
            SettingsItem("Quality", "HD"),
            SettingsItem("Playback", "Play"),
            SettingsItem("Captions", "CC"),
            SettingsItem("Data saving", "Data"),
            SettingsItem("Accessibility", "A11y"),
            SettingsItem("Watch on TV", "TV")
        )
    ),
    SettingsGroup(
        title = "Help and policy",
        items = listOf(
            SettingsItem("Help", "Help"),
            SettingsItem("YouTube Terms of Service", "Terms"),
            SettingsItem("Send feedback", "Send"),
            SettingsItem("About", "Info")
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
        "recommended_create",
        "Recommended ways to create",
        description = "Notify me about recommended trends and ways to create",
        hasToggle = true
    ),
    NotificationSettingsItem(
        "activity_channel",
        "Activity on my channel",
        description = "Notify me about comment and other activity on my channel or videos",
        hasToggle = true
    ),
    NotificationSettingsItem(
        "activity_comments",
        "Activity on my comments",
        description = "Notify me about replies, likes, and other activity on my comments",
        hasToggle = true
    ),
    NotificationSettingsItem(
        "mentions",
        "Mentions",
        description = "Notify me when others mention my channel",
        hasToggle = true
    ),
    NotificationSettingsItem(
        "reused_content",
        "Others reusing my content",
        description = "Notify me when others share, remix, or respond to my content on their channels",
        hasToggle = true
    ),
    NotificationSettingsItem(
        "product_updates",
        "Product updates",
        description = "Updates about YouTube features and tips",
        hasToggle = true
    ),
    NotificationSettingsItem(
        "promotional_content",
        "Promotional content and offers",
        description = "Notify me of promotional content and offers, like member-only perks",
        hasToggle = true
    ),
    NotificationSettingsItem(
        "watch_on_tv",
        "Watch on TV",
        description = "Notify me about activity on connected TVs",
        hasToggle = true
    ),
    NotificationSettingsItem(
        "disable_sounds",
        "Disable sounds & vibrations",
        description = "Silence notifications during the hours you specify",
        hasToggle = true
    ),
    NotificationSettingsItem(
        "new_badges",
        "New badges",
        description = "Notify me when I receive a new badge",
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
    "recommended_create" to true,
    "activity_channel" to true,
    "activity_comments" to true,
    "mentions" to true,
    "reused_content" to true,
    "product_updates" to true,
    "promotional_content" to true,
    "watch_on_tv" to true,
    "disable_sounds" to false,
    "new_badges" to true,
    "loop_video" to false,
    "ambient_mode" to true,
    "stable_volume" to false
)

internal val playSettingsItems = listOf(
    PlaySettingsMenuItem("quality", "Quality", "HD", currentValue = "Auto (720p)"),
    PlaySettingsMenuItem("playback_speed", "Playback speed", "1x", currentValue = "1x"),
    PlaySettingsMenuItem("captions", "Captions", "CC", currentValue = "Chinese"),
    PlaySettingsMenuItem("lock_screen", "Lock screen", "Lock"),
    PlaySettingsMenuItem("more", "More", "More")
)

internal val playSettingsMoreItems = listOf(
    PlaySettingsMenuItem("loop_video", "Loop video", "Loop", hasToggle = true),
    PlaySettingsMenuItem("ambient_mode", "Ambient mode", "Glow", hasToggle = true),
    PlaySettingsMenuItem("stable_volume", "Stable volume", "Vol", hasToggle = true),
    PlaySettingsMenuItem("sleep_timer", "Sleep timer", "Sleep", currentValue = "Off"),
    PlaySettingsMenuItem("watch_in_vr", "Watch in VR", "VR"),
    PlaySettingsMenuItem("help_feedback", "Help & feedback", "Help")
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
