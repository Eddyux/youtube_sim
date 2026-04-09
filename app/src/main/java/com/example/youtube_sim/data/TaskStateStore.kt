package com.example.youtube_sim.data

import android.content.Context
import com.example.youtube_sim.model.EvaluatorMessage
import com.example.youtube_sim.model.InteractionSnapshot
import com.example.youtube_sim.model.toJson
import org.json.JSONArray
import java.io.File

class TaskStateStore(
    context: Context
) {
    private val stateFile = File(context.filesDir, "task_state.json")
    private val messageFile = File(context.filesDir, "messages.json")

    fun reset(snapshot: InteractionSnapshot) {
        write(snapshot)
        writeMessages(JSONArray())
    }

    fun save(snapshot: InteractionSnapshot) {
        write(snapshot)
    }

    fun appendMessage(message: EvaluatorMessage) {
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

    private fun write(snapshot: InteractionSnapshot) {
        stateFile.writeText(snapshot.toJson().toString(2))
    }

    private fun writeMessages(messages: JSONArray) {
        messageFile.writeText(messages.toString(2))
    }
}
