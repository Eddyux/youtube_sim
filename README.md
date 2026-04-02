# youtube_sim

一个基于 Jetpack Compose 的本地静态 YouTube 模拟项目，当前按 `需求.md` 和 `UIReference/` 截图完成了首页、订阅页、个人页、设置页（含 General、Notifications 子页面）以及视频播放页的静态实现。

## 已实现

- `Home` 首页，含 `All / Music / Apple / Live` 四个内容流
- 顶部 YouTube 风格品牌栏与分类标签
- `Subscriptions` 分类订阅列表页
- `You` 页，含 `History / Playlists / Settings / Your videos` 结构
- `Settings` 设置页，各设置项可点击
  - `General` 通用设置页（含 Appearance、Rotate Shorts、Playback in feeds、Voice search language、Location、Restricted Mode、Enable stats for nerds、Earn badges 等设置项与开关）
  - `Notifications` 通知设置页（含 Scheduled digest、Subscriptions、Channel settings、Recommended videos、Product updates、Watch on TV 等通知开关）
- `Video Play` 视频播放页
  - 视频播放区域（渐变占位 + 播放按钮 + 进度条 + 时长标签）
  - 视频信息（标题、播放量、频道信息、Subscribe 按钮）
  - 操作按钮行（点赞、踩、分享、保存）
  - 评论区预览
  - 相关视频推荐列表
  - 播放设置底部弹窗（Quality、Playback speed、Captions、Lock screen、More）
  - 更多设置底部弹窗（Loop video、Ambient mode、Stable volume、Sleep timer、Watch in VR、Help & feedback）
- `Shorts` 仍为占位页
- 未实现点击项统一跳转到"开发中"占位页
- 使用 `app/src/main/assets/data/home_feed.json` 作为静态内容源
- 基于 Presenter 驱动的简单 MVP 分层

## 目录结构

- `app/src/main/java/com/example/youtube_sim/model` — 数据模型
- `app/src/main/java/com/example/youtube_sim/data` — 数据源
- `app/src/main/java/com/example/youtube_sim/presenter` — Presenter 层（含合约、Presenter、静态数据）
- `app/src/main/java/com/example/youtube_sim/view` — 视图层
  - `view/YoutubeApp.kt` — 根路由
  - `view/component/AppChrome.kt` — 顶栏、底栏、ChipRow
  - `view/component/FeedComponents.kt` — 首页 Feed 卡片
  - `view/component/CollectionScreens.kt` — 订阅页、设置页
  - `view/component/SupportingScreens.kt` — 占位页、You 页
  - `view/component/SettingsSubScreens.kt` — General、Notifications 设置子页
  - `view/component/VideoPlayScreen.kt` — 视频播放页
  - `view/component/PlaySettingsSheets.kt` — 播放设置底部弹窗
- `app/src/main/assets/data` — 静态 JSON 数据

## 构建

```powershell
.\gradlew.bat assembleDebug
```

## 当前说明

- 视频封面目前使用渐变占位图，不依赖外部图片资源
- `Podcasts`、顶部操作区、`History / Playlists / Your videos` 详情页仍为占位入口
- 如果设备里还保留旧的 `com.example.test05`，它会和当前 `com.example.youtube_sim` 并存，需要手动卸载旧包避免混淆
- 后续如果你补充更多截图或页面要求，可以继续按现有分层扩展
