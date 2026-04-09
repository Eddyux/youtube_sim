# youtube_sim

A local-asset YouTube-style Android app built with Jetpack Compose. The UI follows the screenshots in `UIReference/` and the screen descriptions in `需求.md`.

## What is in the app

- `Home` keeps the main chip flow with `All`, `Podcasts`, `Music`, `Apple`, and `Live`.
- `Subscriptions` stays available as its own bottom-tab screen.
- `You` now keeps the requirement-based layout: account header, history video previews, playlist covers, `Your videos`, `Movies`, and a Premium promo card.
- `Settings` now includes `General`, `Notifications`, `Languages`, and `Quality` subpages.
- `Search` filters the local asset library by title and creator from the top-right search entry.
- `Notifications` now has both the settings subpage and the empty-state inbox used by the main bell buttons.
- `Channel` now includes dynamic creator pages for the local-feed authors, with a working `Subscribe` / `Subscribed` toggle.
- `Video Play` keeps the player, visible seek bar, comments entry, related videos, and playback settings sheets, including the current-video quality menu.
- `Task logging` now writes a fresh `task_state.json` snapshot plus action-level `messages.json` entries on each launch so evaluation scripts can verify searches, likes, saves, subscriptions, comments, and page visits.
- `Shorts` uses a vertical one-video-at-a-time feed backed by local asset videos.

## Local video support

- Local videos are loaded from `app/src/main/assets/data/vedio/`.
- Feed entries in `app/src/main/assets/data/home_feed.json` point to those asset files.
- Playback uses Media3 ExoPlayer through `app/src/main/java/com/example/youtube_sim/view/component/AssetVideoViews.kt`.
- Video cards, history cards, playlist cards, and related items render thumbnails from local video frames when possible.

## Main files

- `app/src/main/java/com/example/youtube_sim/model` - UI models
- `app/src/main/java/com/example/youtube_sim/data` - asset-backed repository
- `app/src/main/java/com/example/youtube_sim/presenter` - presenter state and screen data
- `app/src/main/java/com/example/youtube_sim/data/TaskStateStore.kt` - internal JSON snapshot writer for evaluator-visible app state
- `app/src/main/java/com/example/youtube_sim/view/YoutubeApp.kt` - root app routing
- `app/src/main/java/com/example/youtube_sim/view/component/AppChrome.kt` - shared top and bottom chrome
- `app/src/main/java/com/example/youtube_sim/view/component/FeedComponents.kt` - home feed UI
- `app/src/main/java/com/example/youtube_sim/view/component/CollectionScreens.kt` - subscriptions UI
- `app/src/main/java/com/example/youtube_sim/view/component/PlaceholderScreen.kt` - reusable in-development screen
- `app/src/main/java/com/example/youtube_sim/view/component/LibraryScreens.kt` - history and playlist screens
- `app/src/main/java/com/example/youtube_sim/view/component/SettingsScreen.kt` - settings list
- `app/src/main/java/com/example/youtube_sim/view/component/SettingsSubScreens.kt` - general and notifications screens
- `app/src/main/java/com/example/youtube_sim/view/component/LanguageSettingsScreen.kt` - languages page and app language dialog
- `app/src/main/java/com/example/youtube_sim/view/component/QualitySettingsScreen.kt` - global video quality preferences
- `app/src/main/java/com/example/youtube_sim/view/component/VideoPlayScreen.kt` - watch screen overlays
- `app/src/main/java/com/example/youtube_sim/view/component/PlaySettingsSheets.kt` - playback settings sheets
- `app/src/main/java/com/example/youtube_sim/view/component/PlayCurrentVideoQualitySheet.kt` - current-video quality chooser
- `app/src/main/java/com/example/youtube_sim/view/component/CommentsSheet.kt` - comments bottom sheet
- `app/src/main/java/com/example/youtube_sim/view/component/ChannelScreen.kt` - creator homepage
- `app/src/main/java/com/example/youtube_sim/view/component/SearchScreen.kt` - local library search
- `app/src/main/java/com/example/youtube_sim/view/component/NotificationInboxScreen.kt` - notifications empty state
- `app/src/main/java/com/example/youtube_sim/view/component/OverflowMenus.kt` - shared history and playlist overflow sheets
- `app/src/main/java/com/example/youtube_sim/view/component/ShortsScreen.kt` - shorts pager UI
- `app/src/main/java/com/example/youtube_sim/view/component/YouScreen.kt` - You tab layout and history previews
- `auto_test/` - self-contained task validators `eval_1.py` through `eval_30.py`

## Build

```powershell
$env:GRADLE_USER_HOME='C:\\androidproject\\android_template\\.gradle-home'
$env:ANDROID_USER_HOME='C:\\androidproject\\android_template\\.android'
.\gradlew.bat assembleDebug
```

## Latest update

- Restored the `Live` home tab and kept it visible in the home chip row.
- Brought the `You` page back in line with the requirement and reference image without removing the rest of the app navigation.
- Added the missing `Your videos`, `Movies`, playlist `+` action, and Premium promo block to the `You` page.
- Preserved and refreshed `Settings`, `General`, `Notifications`, playback settings, and comments instead of trimming them away.
- Expanded the notifications settings list so it better matches the reference screen.
- Added the `Languages` page with the `App language` selection dialog and placeholder entry points for preferred-language follow-up work.
- Added the `Quality` settings page plus the in-player `Quality for current video` bottom sheet.
- Removed the duplicated `History`, `Playlists`, and `Settings` rows from the bottom of the `You` page.
- Switched the `General`, `Notifications`, and `Quality` settings screens to a light background style.
- Switched the `Languages` page and its app-language dialog to the same light settings style.
- Replaced garbled button and icon labels with readable text across the restored screens.
- Added the missing history and playlist overflow menus, including the `Remove from watch history`, `Remove from Liked videos`, and `Remove from Watch later` action sheets.
- Added local-library search plus a dedicated top-bell notifications inbox page based on `UIReference/ling.jpg`.
- Added a Jay Chou creator homepage with a real subscribe state toggle and wired it from creator and channel entry points.
- Added bottom-right duration chips to the You-page preview windows so they match the requirement more closely.
- Expanded creator routing so more feed authors open real channel pages instead of falling through to placeholders.
- Wired the overflow menus to real in-memory state changes for history removal and watch-later / playlist updates.
- Removed the live-tab Iran breaking-news card and replaced the remaining SpaceX / LCK entries with screenshot-backed live pages using `app/src/main/assets/data/live/spacex_launch.png` and `app/src/main/assets/data/live/lck_spring_2026.png`.
- Adjusted search so the results list stays empty before the user enters a query.
- Reduced the top whitespace on `History`, `Watch later`, and `Liked videos`, and made dismissing settings subpages return to the main `Settings` screen first.
- Tightened the top inset spacing again on `History`, `Watch later`, and `Liked videos` to better match the reference density.
- Moved the status-bar spacing from the whole list to the library top bar itself so those three pages sit even tighter.
- Removed the `View all` button from the `Playlists` section on the `You` page.
- Aligned the top spacing of `History`, `Watch later`, and `Liked videos` with the `You` page.
- Switched the `History` section entry on the `You` page to a title-plus-arrow trigger and stretched `Search watch history` to full width.
- Matched the `Playlists` section to the same title-plus-arrow pattern and added a dedicated playlists overview screen based on `UIReference/playlists.jpg`.
- Removed only the `New` card from the `Playlists` row on the `You` page and kept the small `+` action in the header.
- Removed the duplicate bottom `History` and `Playlists` rows from the `You` page while keeping the upper sections unchanged.
- Added real like / save toggles, comment submission, and interaction logging so task evaluators can inspect app-side state through `files/task_state.json` and `files/messages.json`.
- Reset the evaluator-visible state on each fresh launch, while keeping the requested default watch-later item, Jay Chou subscribe baseline, and notifications defaults intact.
- Adjusted the related-videos list and watch-later duration labels to line up with the new detection tasks.
- Added self-contained `auto_test/eval_1.py` through `auto_test/eval_30.py` to validate the current action-based and answer-based tasks from `zhiling.md`.
