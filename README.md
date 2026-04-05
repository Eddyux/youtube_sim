# youtube_sim

A local-asset YouTube-style Android app built with Jetpack Compose. The UI follows the screenshots in `UIReference/` and the screen descriptions in `需求.md`.

## What is in the app

- `Home` keeps the main chip flow with `All`, `Podcasts`, `Music`, `Apple`, and `Live`.
- `Subscriptions` stays available as its own bottom-tab screen.
- `You` now keeps the requirement-based layout: account header, history covers, playlist covers, `Your videos`, `Movies`, and a Premium promo card.
- `Settings` stays reachable, with `General` and `Notifications` subpages preserved.
- `Video Play` keeps the player, visible seek bar, comments entry, related videos, and playback settings sheets.
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
- `app/src/main/java/com/example/youtube_sim/view/YoutubeApp.kt` - root app routing
- `app/src/main/java/com/example/youtube_sim/view/component/AppChrome.kt` - shared top and bottom chrome
- `app/src/main/java/com/example/youtube_sim/view/component/FeedComponents.kt` - home feed UI
- `app/src/main/java/com/example/youtube_sim/view/component/CollectionScreens.kt` - subscriptions UI
- `app/src/main/java/com/example/youtube_sim/view/component/SupportingScreens.kt` - You screen and placeholder UI
- `app/src/main/java/com/example/youtube_sim/view/component/LibraryScreens.kt` - history and playlist screens
- `app/src/main/java/com/example/youtube_sim/view/component/SettingsScreen.kt` - settings list
- `app/src/main/java/com/example/youtube_sim/view/component/SettingsSubScreens.kt` - general and notifications screens
- `app/src/main/java/com/example/youtube_sim/view/component/VideoPlayScreen.kt` - watch screen overlays
- `app/src/main/java/com/example/youtube_sim/view/component/PlaySettingsSheets.kt` - playback settings sheets
- `app/src/main/java/com/example/youtube_sim/view/component/CommentsSheet.kt` - comments bottom sheet
- `app/src/main/java/com/example/youtube_sim/view/component/ShortsScreen.kt` - shorts pager UI

## Build

```powershell
.\gradlew.bat assembleDebug
```

## Latest update

- Restored the `Live` home tab and kept it visible in the home chip row.
- Brought the `You` page back in line with the requirement and reference image without removing the rest of the app navigation.
- Added the missing `Your videos`, `Movies`, playlist `+` action, and Premium promo block to the `You` page.
- Preserved and refreshed `Settings`, `General`, `Notifications`, playback settings, and comments instead of trimming them away.
- Expanded the notifications settings list so it better matches the reference screen.
- Replaced garbled button and icon labels with readable text across the restored screens.
