package com.example.youtube_sim.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = YoutubeRed,
    secondary = YoutubeGray,
    background = YoutubeDark,
    surface = YoutubeDark,
    onPrimary = YoutubeSurface,
    onSecondary = YoutubeSurface,
    onBackground = YoutubeSurface,
    onSurface = YoutubeSurface
)

private val LightColorScheme = lightColorScheme(
    primary = YoutubeRed,
    secondary = YoutubeGray,
    background = YoutubeSurface,
    surface = YoutubeSurface,
    onPrimary = YoutubeSurface,
    onSecondary = YoutubeDark,
    onBackground = YoutubeDark,
    onSurface = YoutubeDark
)

@Composable
fun YoutubeSimTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
