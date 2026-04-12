package com.example.youtube_sim.presenter

import com.example.youtube_sim.data.HomeFeedRepository
import com.example.youtube_sim.data.PersistedUiPreferences
import com.example.youtube_sim.data.TaskStateStoreDataSource
import com.example.youtube_sim.model.EvaluatorMessage
import com.example.youtube_sim.model.FeedItem
import com.example.youtube_sim.model.FeedItemType
import com.example.youtube_sim.model.HomeTabContent
import com.example.youtube_sim.model.InteractionSnapshot
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class YoutubePresenterLoggingTest {

    @Test
    fun task31FlowWritesCurrentVideoQualityAndPlaybackToggleLogs() {
        val store = FakeTaskStateStore()
        createPresenter(store).apply {
            onFeedItemSelected("music-jay-chou-nocturne")
            onSelectionChanged("quality_current_video", "higher_picture_quality")
            onToggle("loop_video")
            onToggle("stable_volume")
        }

        assertTrue(
            store.hasMessage(
                action = "select_option",
                page = "video_play:music-jay-chou-nocturne",
                extraData = mapOf(
                    "group_key" to "quality_current_video",
                    "option_key" to "higher_picture_quality",
                    "item_id" to "music-jay-chou-nocturne"
                )
            )
        )
        assertTrue(
            store.hasMessage(
                action = "toggle_setting",
                page = "video_play:music-jay-chou-nocturne",
                extraData = mapOf(
                    "key" to "loop_video",
                    "enabled" to "true",
                    "item_id" to "music-jay-chou-nocturne"
                )
            )
        )
        assertTrue(
            store.hasMessage(
                action = "toggle_setting",
                page = "video_play:music-jay-chou-nocturne",
                extraData = mapOf(
                    "key" to "stable_volume",
                    "enabled" to "true",
                    "item_id" to "music-jay-chou-nocturne"
                )
            )
        )
        assertEquals("higher_picture_quality", store.lastSnapshot.selectedOptions["quality_current_video"])
        assertTrue(store.lastSnapshot.toggleStates.getValue("loop_video"))
        assertTrue(store.lastSnapshot.toggleStates.getValue("stable_volume"))
    }

    @Test
    fun task32FlowKeepsPersistedPlaybackPreferencesAcrossRelaunch() {
        val store = FakeTaskStateStore()
        createPresenter(store).apply {
            onFeedItemSelected("music-blue-porcelain")
            onSelectionChanged("quality_current_video", "higher_picture_quality")
            onToggle("ambient_mode")
        }

        assertTrue(
            store.hasMessage(
                action = "toggle_setting",
                page = "video_play:music-blue-porcelain",
                extraData = mapOf(
                    "key" to "ambient_mode",
                    "enabled" to "false",
                    "item_id" to "music-blue-porcelain"
                )
            )
        )

        createPresenter(store)
        assertEquals("higher_picture_quality", store.lastSnapshot.selectedOptions["quality_current_video"])
        assertFalse(store.lastSnapshot.toggleStates.getValue("ambient_mode"))
    }

    @Test
    fun task33FlowLogsGlobalQualitySelectionAndClearsCommentsOnRelaunch() {
        val store = FakeTaskStateStore()
        createPresenter(store).apply {
            onSettingsItemSelected("Quality")
            onSelectionChanged("quality_mobile", "higher_picture_quality")
            onFeedItemSelected("music-blue-porcelain")
            onCommentSubmitted("music-blue-porcelain", "这个MV真清晰")
        }

        assertTrue(
            store.hasMessage(
                action = "select_option",
                page = "quality",
                extraData = mapOf(
                    "group_key" to "quality_mobile",
                    "option_key" to "higher_picture_quality"
                )
            )
        )
        assertTrue(
            store.hasMessage(
                action = "submit_comment",
                page = "comments_sheet",
                extraData = mapOf(
                    "item_id" to "music-blue-porcelain",
                    "text" to "这个MV真清晰"
                )
            )
        )
        assertEquals(listOf("这个MV真清晰"), store.lastSnapshot.postedComments["music-blue-porcelain"])

        createPresenter(store)
        assertEquals("higher_picture_quality", store.lastSnapshot.selectedOptions["quality_mobile"])
        assertFalse(store.lastSnapshot.postedComments.containsKey("music-blue-porcelain"))
    }

    @Test
    fun task34FlowLogsLikesSavesAndCommentsForBothJayChouVideos() {
        val store = FakeTaskStateStore()
        createPresenter(store).apply {
            onFeedItemSelected("music-jay-chou-nocturne")
            onVideoLikeToggle("music-jay-chou-nocturne")
            onVideoSaveToggle("music-jay-chou-nocturne")
            onCommentSubmitted("music-jay-chou-nocturne", "我是周杰伦十年老粉")

            onFeedItemSelected("music-blue-porcelain")
            onVideoLikeToggle("music-blue-porcelain")
            onVideoSaveToggle("music-blue-porcelain")
            onCommentSubmitted("music-blue-porcelain", "这首歌让我想起了那个女孩")
        }

        assertTrue(
            store.hasMessage(
                action = "toggle_video_like",
                extraData = mapOf("item_id" to "music-jay-chou-nocturne", "enabled" to "true")
            )
        )
        assertTrue(
            store.hasMessage(
                action = "toggle_video_save",
                extraData = mapOf("item_id" to "music-jay-chou-nocturne", "enabled" to "true")
            )
        )
        assertTrue(
            store.hasMessage(
                action = "submit_comment",
                page = "comments_sheet",
                extraData = mapOf("item_id" to "music-jay-chou-nocturne", "text" to "我是周杰伦十年老粉")
            )
        )
        assertTrue(
            store.hasMessage(
                action = "toggle_video_like",
                extraData = mapOf("item_id" to "music-blue-porcelain", "enabled" to "true")
            )
        )
        assertTrue(
            store.hasMessage(
                action = "toggle_video_save",
                extraData = mapOf("item_id" to "music-blue-porcelain", "enabled" to "true")
            )
        )
        assertTrue(
            store.hasMessage(
                action = "submit_comment",
                page = "comments_sheet",
                extraData = mapOf("item_id" to "music-blue-porcelain", "text" to "这首歌让我想起了那个女孩")
            )
        )
        assertTrue(store.lastSnapshot.likedVideoIds.contains("music-jay-chou-nocturne"))
        assertTrue(store.lastSnapshot.likedVideoIds.contains("music-blue-porcelain"))
        assertTrue(store.lastSnapshot.savedVideoIds.contains("music-jay-chou-nocturne"))
        assertTrue(store.lastSnapshot.savedVideoIds.contains("music-blue-porcelain"))
    }

    @Test
    fun task35FlowLogsTaylorSwiftLikeSaveAndComment() {
        val store = FakeTaskStateStore()
        createPresenter(store).apply {
            onFeedItemSelected("all-taylor-ophelia")
            onVideoLikeToggle("all-taylor-ophelia")
            onVideoSaveToggle("all-taylor-ophelia")
            onCommentSubmitted("all-taylor-ophelia", "虽然你的歌很好听，但是我还是喜欢听周杰伦的歌")
        }

        assertTrue(
            store.hasMessage(
                action = "toggle_video_like",
                extraData = mapOf("item_id" to "all-taylor-ophelia", "enabled" to "true")
            )
        )
        assertTrue(
            store.hasMessage(
                action = "toggle_video_save",
                extraData = mapOf("item_id" to "all-taylor-ophelia", "enabled" to "true")
            )
        )
        assertTrue(
            store.hasMessage(
                action = "submit_comment",
                page = "comments_sheet",
                extraData = mapOf(
                    "item_id" to "all-taylor-ophelia",
                    "text" to "虽然你的歌很好听，但是我还是喜欢听周杰伦的歌"
                )
            )
        )
        assertTrue(store.lastSnapshot.likedVideoIds.contains("all-taylor-ophelia"))
        assertTrue(store.lastSnapshot.savedVideoIds.contains("all-taylor-ophelia"))
    }

    private fun createPresenter(store: FakeTaskStateStore): YoutubePresenter {
        return YoutubePresenter(
            repository = fakeHomeFeedRepository(),
            taskStateStore = store
        )
    }

    private fun fakeHomeFeedRepository(): HomeFeedRepository = object : HomeFeedRepository {
        override fun loadTabs(): List<HomeTabContent> {
            return listOf(
                HomeTabContent(
                    key = "all",
                    label = "All",
                    items = listOf(
                        feedItem(
                            id = "all-taylor-ophelia",
                            title = "Taylor Swift - The Fate of Ophelia (Official Music Video)",
                            creator = "Taylor Swift"
                        )
                    )
                ),
                HomeTabContent(
                    key = "music",
                    label = "Music",
                    items = listOf(
                        feedItem(
                            id = "music-jay-chou-nocturne",
                            title = "周杰伦 Jay Chou 夜曲",
                            creator = "Jay Chou"
                        ),
                        feedItem(
                            id = "music-blue-porcelain",
                            title = "周杰伦 Jay Chou 青花瓷",
                            creator = "Jay Chou"
                        ),
                        feedItem(
                            id = "music-taylor-ophelia",
                            title = "Taylor Swift - The Fate of Ophelia (Official Music Video)",
                            creator = "Taylor Swift"
                        )
                    )
                )
            )
        }
    }

    private fun feedItem(id: String, title: String, creator: String) = FeedItem(
        id = id,
        type = FeedItemType.VIDEO,
        title = title,
        creator = creator,
        metadata = "1M views - 1 day ago",
        supportingText = null,
        actionText = "4:00",
        badgeText = null,
        sectionTitle = null,
        thumbnailLabel = "Thumbnail",
        assetPath = "data/video/$id.mp4",
        imagePath = null,
        accentStart = "#111111",
        accentEnd = "#222222"
    )

    private class FakeTaskStateStore : TaskStateStoreDataSource {
        var persistedPreferences = PersistedUiPreferences(
            toggleStates = emptyMap(),
            selectedOptions = emptyMap()
        )
        var lastSnapshot = InteractionSnapshot(
            currentRootTab = "home",
            selectedHomeChipKey = "all",
            activeOverlay = null,
            searchQuery = "",
            toggleStates = emptyMap(),
            selectedOptions = emptyMap(),
            subscribedChannels = emptySet(),
            likedVideoIds = emptyList(),
            savedVideoIds = emptyList(),
            playlistItems = emptyMap(),
            postedComments = emptyMap(),
            lastPlayedVideoId = null,
            events = emptyList()
        )
        val messages = mutableListOf<EvaluatorMessage>()

        override fun reset(snapshot: InteractionSnapshot) {
            lastSnapshot = snapshot
            messages.clear()
        }

        override fun save(snapshot: InteractionSnapshot) {
            lastSnapshot = snapshot
        }

        override fun appendMessage(message: EvaluatorMessage) {
            messages += message
        }

        override fun loadUiPreferences(): PersistedUiPreferences = persistedPreferences

        override fun saveUiPreferences(
            toggleStates: Map<String, Boolean>,
            selectedOptions: Map<String, String>
        ) {
            persistedPreferences = PersistedUiPreferences(
                toggleStates = toggleStates,
                selectedOptions = selectedOptions
            )
        }

        fun hasMessage(
            action: String,
            page: String? = null,
            extraData: Map<String, String>
        ): Boolean {
            return messages.any { message ->
                if (message.action != action) return@any false
                if (page != null && message.page != page) return@any false
                extraData.all { (key, value) -> message.extraData[key] == value }
            }
        }
    }
}
