package com.example.youtube_sim.presenter

import com.example.youtube_sim.model.PlaylistDetail

internal fun refreshPlaylistDetails(details: List<PlaylistDetail>): List<PlaylistDetail> {
    return details.map { detail ->
        detail.copy(metadata = playlistMetadata(detail.key, detail.itemIds.size))
    }
}

private fun playlistMetadata(key: String, count: Int): String {
    val videoLabel = if (count == 1) "1 video" else "$count videos"
    return when (key) {
        "watch_later", "liked_videos" -> "Playlist - Private - $videoLabel - No views"
        else -> "Playlist - $videoLabel"
    }
}
