package com.example.youtube_sim.data

import android.content.Context
import com.example.youtube_sim.model.EvaluatorMessage
import com.example.youtube_sim.model.InteractionSnapshot
import com.example.youtube_sim.model.toJson
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

data class PersistedUiPreferences(
    val toggleStates: Map<String, Boolean>,
    val selectedOptions: Map<String, String>
)

interface TaskStateStoreDataSource {
    fun reset(snapshot: InteractionSnapshot)
    fun save(snapshot: InteractionSnapshot)
    fun appendMessage(message: EvaluatorMessage)
    fun loadUiPreferences(): PersistedUiPreferences
    fun saveUiPreferences(
        toggleStates: Map<String, Boolean>,
        selectedOptions: Map<String, String>
    )
}

class TaskStateStore(
    private val filesDir: File
) : TaskStateStoreDataSource {
    constructor(context: Context) : this(context.filesDir)

    private val stateFile = File(filesDir, "task_state.json")
    private val messageFile = File(filesDir, "messages.json")
    private val preferencesFile = File(filesDir, "ui_preferences.json")

    override fun reset(snapshot: InteractionSnapshot) {
        write(snapshot)
        writeMessages(JSONArray())
    }

    override fun save(snapshot: InteractionSnapshot) {
        write(snapshot)
    }

    override fun appendMessage(message: EvaluatorMessage) {
        val messages = runCatching {
            if (messageFile.exists()) {
                JSONArray(messageFile.readText())
            } else {
                JSONArray()
            }
        }.getOrDefault(JSONArray())
        messages.put(message.toJson())
        writeMessages(messages)
    }

    override fun loadUiPreferences(): PersistedUiPreferences {
        val payload = runCatching {
            if (preferencesFile.exists()) {
                JSONObject(preferencesFile.readText())
            } else {
                JSONObject()
            }
        }.getOrDefault(JSONObject())

        return PersistedUiPreferences(
            toggleStates = payload.optJSONObject("toggle_states").toBooleanMap(),
            selectedOptions = payload.optJSONObject("selected_options").toStringMap()
        )
    }

    override fun saveUiPreferences(
        toggleStates: Map<String, Boolean>,
        selectedOptions: Map<String, String>
    ) {
        val payload = JSONObject().apply {
            put("toggle_states", JSONObject(toggleStates))
            put("selected_options", JSONObject(selectedOptions))
        }
        preferencesFile.writeText(payload.toString(2))
    }

    private fun write(snapshot: InteractionSnapshot) {
        stateFile.writeText(snapshot.toJson().toString(2))
    }

    private fun writeMessages(messages: JSONArray) {
        messageFile.writeText(messages.toString(2))
    }
}

private fun JSONObject?.toBooleanMap(): Map<String, Boolean> {
    if (this == null) return emptyMap()
    val result = linkedMapOf<String, Boolean>()
    val iterator = keys()
    while (iterator.hasNext()) {
        val key = iterator.next()
        result[key] = optBoolean(key)
    }
    return result
}

private fun JSONObject?.toStringMap(): Map<String, String> {
    if (this == null) return emptyMap()
    val result = linkedMapOf<String, String>()
    val iterator = keys()
    while (iterator.hasNext()) {
        val key = iterator.next()
        val value = optString(key).trim()
        if (value.isNotEmpty()) {
            result[key] = value
        }
    }
    return result
}
