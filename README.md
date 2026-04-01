# youtube_sim

一个基于 Jetpack Compose 的本地静态 YouTube 模拟项目，当前按 `需求.md` 和 `UIReference/` 截图完成了首页、订阅页、个人页与设置页的第一版静态实现。

## 已实现

- `Home` 首页，含 `All / Music / Apple / Live` 四个内容流
- 顶部 YouTube 风格品牌栏与分类标签
- `Subscriptions` 分类订阅列表页
- `You` 页，含 `History / Playlists / Settings / Your videos` 结构
- `Settings` 设置页
- `Shorts` 仍为占位页
- 未实现点击项统一跳转到“开发中”占位页
- 使用 `app/src/main/assets/data/home_feed.json` 作为静态内容源
- 基于 Presenter 驱动的简单 MVP 分层

## 目录结构

- `app/src/main/java/com/example/youtube_sim/model`
- `app/src/main/java/com/example/youtube_sim/data`
- `app/src/main/java/com/example/youtube_sim/presenter`
- `app/src/main/java/com/example/youtube_sim/view`
- `app/src/main/assets/data`

## 构建

```powershell
.\gradlew.bat assembleDebug
```

## 当前说明

- 视频封面目前使用渐变占位图，不依赖外部图片资源
- `Podcasts`、顶部操作区、视频播放页、`History / Playlists / Your videos` 详情页仍为占位入口
- 如果设备里还保留旧的 `com.example.test05`，它会和当前 `com.example.youtube_sim` 并存，需要手动卸载旧包避免混淆
- 后续如果你补充更多截图或页面要求，可以继续按现有分层扩展
