package com.example.youtube_sim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.example.youtube_sim.data.AssetHomeFeedRepository
import com.example.youtube_sim.presenter.YoutubePresenter
import com.example.youtube_sim.ui.theme.YoutubeSimTheme
import com.example.youtube_sim.view.YoutubeApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val presenter = remember { YoutubePresenter(AssetHomeFeedRepository(applicationContext)) }
            YoutubeSimTheme {
                YoutubeApp(presenter = presenter)
            }
        }
    }
}
