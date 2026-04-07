package com.example.youtube_sim.model

enum class FeedItemType {
    AD,
    PROMO,
    VIDEO,
    LIVE
}

enum class RootTab(val label: String, val emoji: String) {
    HOME("Home", "H"),
    SHORTS("Shorts", "S"),
    SUBSCRIPTIONS("Subscriptions", "Sub"),
    YOU("You", "Y")
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
    val assetPath: String?,
    val imagePath: String?,
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
    val key: String,
    val title: String,
    val privacy: String,
    val count: String
)

data class HistoryEntry(
    val itemId: String,
    val note: String? = null
)

data class HistorySection(
    val title: String,
    val entries: List<HistoryEntry>
)

data class PlaylistDetail(
    val key: String,
    val title: String,
    val metadata: String,
    val description: String,
    val itemIds: List<String>
)

data class OverflowMenuAction(
    val key: String,
    val label: String
)

data class ChannelProfile(
    val key: String,
    val title: String,
    val handle: String,
    val subscribers: String,
    val heroItemId: String,
    val featuredItemId: String,
    val videoItemIds: List<String>,
    val description: String
)

data class SettingsGroup(
    val title: String,
    val items: List<SettingsItem>
)

data class SettingsItem(
    val label: String,
    val emoji: String
)

data class GeneralSettingsItem(
    val key: String,
    val label: String,
    val subtitle: String? = null,
    val hasToggle: Boolean = false
)

data class NotificationSettingsItem(
    val key: String,
    val label: String,
    val description: String? = null,
    val hasToggle: Boolean = false
)

data class PlaySettingsMenuItem(
    val key: String,
    val label: String,
    val emoji: String,
    val currentValue: String? = null,
    val hasToggle: Boolean = false
)

data class VideoComment(
    val author: String,
    val handle: String,
    val text: String,
    val likes: String,
    val timeAgo: String,
    val replyCount: String? = null
)
