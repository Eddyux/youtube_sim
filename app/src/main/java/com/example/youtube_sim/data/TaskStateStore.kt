package com.example.youtube_sim.data

import android.content.Context
import com.example.youtube_sim.model.InteractionSnapshot
import com.example.youtube_sim.model.toJson
import java.io.File

class TaskStateStore(
    context: Context
) {
    private val stateFile = File(context.filesDir, "task_state.json")

    fun reset(snapshot: InteractionSnapshot) {
        write(snapshot)
    }

    fun save(snapshot: InteractionSnapshot) {
        write(snapshot)
    }

    private fun write(snapshot: InteractionSnapshot) {
        stateFile.writeText(snapshot.toJson().toString(2))
    }
}
