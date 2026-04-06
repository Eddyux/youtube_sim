package com.example.youtube_sim.presenter

import com.example.youtube_sim.model.GeneralSettingsItem
import com.example.youtube_sim.model.LanguageOption
import com.example.youtube_sim.model.NotificationSettingsItem
import com.example.youtube_sim.model.PlaySettingsMenuItem
import com.example.youtube_sim.model.QualityOption
import com.example.youtube_sim.model.QualityPreferenceSection
import com.example.youtube_sim.model.SettingsGroup
import com.example.youtube_sim.model.SettingsItem

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

internal val languageOptions = listOf(
    LanguageOption("english_china", "English (China)", "English (China)", note = "System Default"),
    LanguageOption("afrikaans", "Afrikaans", "Afrikaans"),
    LanguageOption("albanian", "Albanian", "Shqip"),
    LanguageOption("amharic", "Amharic", "አማርኛ"),
    LanguageOption("arabic", "Arabic", "العربية"),
    LanguageOption("armenian", "Armenian", "Հայերեն"),
    LanguageOption("assamese", "Assamese", "অসমীয়া"),
    LanguageOption("azerbaijani", "Azerbaijani", "Azərbaycan")
)

private val defaultQualityOptions = listOf(
    QualityOption(
        key = "auto",
        label = "Auto (recommended)",
        description = "Adjusts to give you the best experience for your conditions"
    ),
    QualityOption(
        key = "higher_picture_quality",
        label = "Higher picture quality",
        description = "Uses more data"
    ),
    QualityOption(
        key = "data_saver",
        label = "Data saver",
        description = "Lower picture quality"
    )
)

internal val qualityPreferenceSections = listOf(
    QualityPreferenceSection(
        key = "quality_mobile",
        title = "Video quality on mobile networks",
        options = defaultQualityOptions
    ),
    QualityPreferenceSection(
        key = "quality_wifi",
        title = "Video quality on Wi-Fi",
        options = defaultQualityOptions
    ),
    QualityPreferenceSection(
        key = "quality_audio",
        title = "Audio quality",
        options = listOf(
            QualityOption(
                key = "higher_audio_quality",
                label = "Higher audio quality",
                description = "Available for some videos with YouTube Premium. Uses more data."
            ),
            QualityOption(
                key = "normal",
                label = "Normal",
                description = "Uses less data"
            )
        )
    )
)

internal val currentVideoQualityOptions = defaultQualityOptions + QualityOption(
    key = "advanced",
    label = "Advanced",
    description = "Select a specific resolution"
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

internal val defaultSelections = mapOf(
    "app_language" to "english_china",
    "quality_mobile" to "auto",
    "quality_wifi" to "auto",
    "quality_audio" to "normal",
    "quality_current_video" to "auto"
)

internal val playSettingsMoreItems = listOf(
    PlaySettingsMenuItem("loop_video", "Loop video", "Loop", hasToggle = true),
    PlaySettingsMenuItem("ambient_mode", "Ambient mode", "Glow", hasToggle = true),
    PlaySettingsMenuItem("stable_volume", "Stable volume", "Vol", hasToggle = true),
    PlaySettingsMenuItem("sleep_timer", "Sleep timer", "Sleep", currentValue = "Off"),
    PlaySettingsMenuItem("watch_in_vr", "Watch in VR", "VR"),
    PlaySettingsMenuItem("help_feedback", "Help & feedback", "Help")
)

internal fun resolutionForCurrentVideo(optionKey: String): String = when (optionKey) {
    "higher_picture_quality" -> "720p"
    "data_saver" -> "144p"
    else -> "360p"
}

internal fun buildPlaySettingsItems(
    currentVideoSelection: String,
    currentResolutionLabel: String
): List<PlaySettingsMenuItem> {
    val qualityValue = when (currentVideoSelection) {
        "higher_picture_quality" -> "Higher picture quality"
        "data_saver" -> "Data saver"
        else -> "Auto ($currentResolutionLabel)"
    }

    return listOf(
        PlaySettingsMenuItem("quality", "Quality", "HD", currentValue = qualityValue),
        PlaySettingsMenuItem("playback_speed", "Playback speed", "1x", currentValue = "1x"),
        PlaySettingsMenuItem("captions", "Captions", "CC", currentValue = "Chinese"),
        PlaySettingsMenuItem("lock_screen", "Lock screen", "Lock"),
        PlaySettingsMenuItem("more", "More", "More")
    )
}
