package com.example.youtube_sim.model

import org.json.JSONArray
import org.json.JSONObject

data class InteractionEvent(
    val action: String,
    val target: String? = null,
    val details: Map<String, String> = emptyMap(),
    val timestamp: Long = System.currentTimeMillis()
)

data class InteractionSnapshot(
    val currentRootTab: String,
    val selectedHomeChipKey: String,
    val activeOverlay: String?,
    val searchQuery: String,
    val toggleStates: Map<String, Boolean>,
    val selectedOptions: Map<String, String>,
    val subscribedChannels: Set<String>,
    val likedVideoIds: List<String>,
    val savedVideoIds: List<String>,
    val playlistItems: Map<String, List<String>>,
    val postedComments: Map<String, List<String>>,
    val lastPlayedVideoId: String?,
    val events: List<InteractionEvent>
)

fun InteractionSnapshot.toJson(): JSONObject {
    return JSONObject().apply {
        put("current_root_tab", currentRootTab)
        put("selected_home_chip_key", selectedHomeChipKey)
        put("active_overlay", activeOverlay)
        put("search_query", searchQuery)
        put("toggle_states", JSONObject(toggleStates))
        put("selected_options", JSONObject(selectedOptions))
        put("subscribed_channels", JSONArray(subscribedChannels.sorted()))
        put("liked_video_ids", JSONArray(likedVideoIds))
        put("saved_video_ids", JSONArray(savedVideoIds))
        put("playlist_items", playlistItems.toJsonObject())
        put("posted_comments", postedComments.toJsonObject())
        put("last_played_video_id", lastPlayedVideoId)
        put("events", JSONArray().apply {
            events.forEach { event ->
                put(
                    JSONObject().apply {
                        put("action", event.action)
                        put("target", event.target)
                        put("details", JSONObject(event.details))
                        put("timestamp", event.timestamp)
                    }
                )
            }
        })
    }
}

private fun Map<String, List<String>>.toJsonObject(): JSONObject {
    return JSONObject().apply {
        toSortedMap().forEach { (key, values) ->
            put(key, JSONArray(values))
        }
    }
}
