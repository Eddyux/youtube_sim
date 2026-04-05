package com.example.youtube_sim.view.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Comment
import androidx.compose.material.icons.automirrored.rounded.HelpOutline
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material.icons.automirrored.rounded.PlaylistAdd
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.Accessibility
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AccountTree
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.Bedtime
import androidx.compose.material.icons.rounded.Cast
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.ClosedCaption
import androidx.compose.material.icons.rounded.DataSaverOff
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Feedback
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material.icons.rounded.HighQuality
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Loop
import androidx.compose.material.icons.rounded.ManageAccounts
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material.icons.rounded.PlaylistPlay
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.ScreenRotation
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.SmartDisplay
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.Subscriptions
import androidx.compose.material.icons.rounded.ThumbDownOffAlt
import androidx.compose.material.icons.rounded.ThumbUpOffAlt
import androidx.compose.material.icons.rounded.Translate
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material.icons.rounded.VideoLibrary
import androidx.compose.material.icons.rounded.ViewInAr
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.youtube_sim.model.RootTab

internal fun headerActionIcon(key: String): ImageVector = when (key) {
    "cast" -> Icons.Rounded.Cast
    "notifications" -> Icons.Rounded.NotificationsNone
    "search" -> Icons.Rounded.Search
    else -> Icons.Rounded.MoreHoriz
}

internal fun bottomTabIcon(tab: RootTab): ImageVector = when (tab) {
    RootTab.HOME -> Icons.Rounded.Home
    RootTab.SHORTS -> Icons.Rounded.SmartDisplay
    RootTab.SUBSCRIPTIONS -> Icons.Rounded.Subscriptions
    RootTab.YOU -> Icons.Rounded.Person
}

internal fun youEntryIcon(key: String): ImageVector = when (key) {
    "history" -> Icons.Rounded.History
    "playlists" -> Icons.Rounded.PlaylistPlay
    "settings" -> Icons.Rounded.Settings
    "your-videos" -> Icons.Rounded.VideoLibrary
    "movies" -> Icons.Rounded.Movie
    else -> Icons.Rounded.KeyboardArrowRight
}

internal fun settingsItemIcon(label: String): ImageVector = when (label) {
    "General" -> Icons.Rounded.Tune
    "Switch or manage account" -> Icons.Rounded.ManageAccounts
    "Family Center" -> Icons.Rounded.AccountTree
    "Languages" -> Icons.Rounded.Translate
    "Time management" -> Icons.Rounded.Schedule
    "Notifications" -> Icons.Rounded.NotificationsNone
    "Purchases and memberships" -> Icons.Rounded.PlayArrow
    "Billing & payments" -> Icons.Rounded.Payments
    "Manage all history" -> Icons.Rounded.History
    "Your data in YouTube" -> Icons.Rounded.BarChart
    "Privacy" -> Icons.Rounded.Lock
    "Connected apps" -> Icons.Rounded.Apps
    "Try experimental new features" -> Icons.Rounded.LightMode
    "Quality" -> Icons.Rounded.HighQuality
    "Playback" -> Icons.Rounded.PlayCircleOutline
    "Captions" -> Icons.Rounded.ClosedCaption
    "Data saving" -> Icons.Rounded.DataSaverOff
    "Accessibility" -> Icons.Rounded.Accessibility
    "Watch on TV" -> Icons.Rounded.Tv
    "Help" -> Icons.AutoMirrored.Rounded.HelpOutline
    "YouTube Terms of Service" -> Icons.Rounded.Description
    "Send feedback" -> Icons.Rounded.Feedback
    "About" -> Icons.Rounded.Info
    else -> Icons.Rounded.KeyboardArrowRight
}

internal fun playSettingIcon(key: String): ImageVector = when (key) {
    "quality" -> Icons.Rounded.Tune
    "playback_speed" -> Icons.Rounded.Speed
    "captions" -> Icons.Rounded.ClosedCaption
    "lock_screen" -> Icons.Rounded.Lock
    "more" -> Icons.Rounded.Settings
    "loop_video" -> Icons.Rounded.Loop
    "ambient_mode" -> Icons.Rounded.LightMode
    "stable_volume" -> Icons.Rounded.GraphicEq
    "sleep_timer" -> Icons.Rounded.Bedtime
    "watch_in_vr" -> Icons.Rounded.ViewInAr
    "help_feedback" -> Icons.AutoMirrored.Rounded.HelpOutline
    else -> Icons.Rounded.MoreHoriz
}

internal val BackIcon = Icons.AutoMirrored.Rounded.ArrowBack
internal val ChevronDownIcon = Icons.Rounded.KeyboardArrowDown
internal val ChevronRightIcon = Icons.Rounded.KeyboardArrowRight
internal val SettingsIcon = Icons.Rounded.Settings
internal val MoreIcon = Icons.Rounded.MoreVert
internal val InfoIcon = Icons.Rounded.Info
internal val CloseIcon = Icons.Rounded.Close
internal val AddIcon = Icons.Rounded.Add
internal val LikeIcon = Icons.Rounded.ThumbUpOffAlt
internal val DislikeIcon = Icons.Rounded.ThumbDownOffAlt
internal val ShareIcon = Icons.Rounded.Share
internal val SaveIcon = Icons.AutoMirrored.Rounded.PlaylistAdd
internal val CommentIcon = Icons.AutoMirrored.Rounded.Comment
internal val PlayLogoIcon = Icons.Rounded.PlayArrow
internal val CastIcon = Icons.Rounded.Cast
internal val SearchIcon = Icons.Rounded.Search
internal val BellIcon = Icons.Rounded.NotificationsNone
internal val OpenIcon = Icons.AutoMirrored.Rounded.OpenInNew
internal val SendIcon = Icons.AutoMirrored.Rounded.Send
internal val PremiumBadgeIcon = Icons.Rounded.SmartDisplay
internal val PaletteIcon = Icons.Rounded.Palette
internal val RotateIcon = Icons.Rounded.ScreenRotation
internal val LanguageIcon = Icons.Rounded.Language
internal val LocationIcon = Icons.Rounded.LocationOn
internal val RestrictedIcon = Icons.Rounded.Security
internal val StatsIcon = Icons.Rounded.BarChart
internal val BadgesIcon = Icons.Rounded.EmojiEvents
