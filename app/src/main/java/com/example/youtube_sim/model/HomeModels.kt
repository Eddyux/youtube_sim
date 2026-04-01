package com.example.youtube_sim.model

enum class FeedItemType {
    AD,
    PROMO,
    VIDEO,
    LIVE
}

enum class RootTab(val label: String, val emoji: String) {
    HOME("Home", "🏠"),
    SHORTS("Shorts", "🎬"),
    SUBSCRIPTIONS("Subscriptions", "📚"),
    YOU("You", "🙂")
}

data class HomeTabContent(
    val key: String,
    val label: String,
    val items: List<FeedItem>
)

data class FeedItem(
    val id: String,
    val type: FeedItemType,
    val title: String,
    val creator: String,
    val metadata: String,
    val supportingText: String?,
    val actionText: String?,
    val badgeText: String?,
    val sectionTitle: String?,
    val thumbnailLabel: String,
    val accentStart: String,
    val accentEnd: String
)

data class HomeChip(
    val key: String,
    val label: String,
    val implemented: Boolean
)

data class HeaderAction(
    val key: String,
    val emoji: String,
    val label: String
)

data class YouMenuEntry(
    val key: String,
    val label: String,
    val subtitle: String,
    val emoji: String
)

data class SubscriptionGroup(
    val title: String,
    val channels: List<SubscriptionChannel>
)

data class SubscriptionChannel(
    val name: String,
    val verified: Boolean
)

data class HistoryPreview(
    val title: String,
    val accentStart: String,
    val accentEnd: String
)

data class PlaylistPreview(
    val title: String,
    val privacy: String,
    val count: String
)

data class SettingsGroup(
    val title: String,
    val items: List<SettingsItem>
)

data class SettingsItem(
    val label: String,
    val emoji: String
)
