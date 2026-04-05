package com.example.youtube_sim.view.component

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color.parseColor
import android.media.MediaMetadataRetriever
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.youtube_sim.model.FeedItem
import java.io.File
import java.security.MessageDigest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AssetThumbnail(
    item: FeedItem,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    overlay: @Composable BoxScope.() -> Unit = {}
) {
    val bitmap = rememberVideoThumbnail(assetPath = item.assetPath)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(Brush.linearGradient(listOf(item.accentStart.toColorOrFallback(), item.accentEnd.toColorOrFallback())))
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = item.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = contentScale
            )
        } else {
            Text(
                modifier = Modifier.align(Alignment.CenterStart).padding(horizontal = 18.dp),
                text = item.thumbnailLabel,
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
        overlay()
    }
}

@Composable
fun AssetVideoPlayer(
    item: FeedItem,
    modifier: Modifier = Modifier,
    playWhenReady: Boolean = true,
    repeatMode: Int = Player.REPEAT_MODE_OFF,
    resizeMode: Int = AspectRatioFrameLayout.RESIZE_MODE_FIT,
    showControls: Boolean = false,
    keepControlsVisible: Boolean = false,
    overlay: @Composable BoxScope.() -> Unit = {}
) {
    val context = LocalContext.current
    val file = rememberCachedAssetFile(assetPath = item.assetPath)
    val player = remember(file?.absolutePath) {
        file?.let {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(it.toURI().toString()))
                videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                prepare()
            }
        }
    }

    DisposableEffect(player) {
        onDispose { player?.release() }
    }

    LaunchedEffect(player, playWhenReady, repeatMode) {
        player?.repeatMode = repeatMode
        player?.playWhenReady = playWhenReady
        if (playWhenReady) {
            player?.play()
        } else {
            player?.pause()
            player?.seekTo(0)
        }
    }

    Box(modifier = modifier.background(Color.Black)) {
        if (player != null) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { viewContext ->
                    PlayerView(viewContext).apply {
                        useController = showControls
                        controllerShowTimeoutMs = if (keepControlsVisible) 0 else 3_000
                        this.resizeMode = resizeMode
                        this.player = player
                        setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                        keepScreenOn = playWhenReady
                    }
                },
                update = { view ->
                    view.useController = showControls
                    view.controllerShowTimeoutMs = if (keepControlsVisible) 0 else 3_000
                    view.resizeMode = resizeMode
                    view.keepScreenOn = playWhenReady
                    if (view.player !== player) {
                        view.player = player
                    }
                    if (showControls) {
                        view.showController()
                    }
                }
            )
        } else {
            AssetThumbnail(
                item = item,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        overlay()
    }
}

@Composable
private fun rememberVideoThumbnail(assetPath: String?): Bitmap? {
    val context = LocalContext.current
    val result by produceState<Bitmap?>(initialValue = null, key1 = assetPath) {
        value = if (assetPath.isNullOrBlank()) {
            null
        } else {
            withContext(Dispatchers.IO) {
                runCatching {
                    val file = cacheAssetFile(context, assetPath)
                    val retriever = MediaMetadataRetriever()
                    try {
                        retriever.setDataSource(file.absolutePath)
                        retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
                    } finally {
                        retriever.release()
                    }
                }.getOrNull()
            }
        }
    }
    return result
}

@Composable
private fun rememberCachedAssetFile(assetPath: String?): File? {
    val context = LocalContext.current
    val result by produceState<File?>(initialValue = null, key1 = assetPath) {
        value = if (assetPath.isNullOrBlank()) {
            null
        } else {
            withContext(Dispatchers.IO) {
                runCatching { cacheAssetFile(context, assetPath) }.getOrNull()
            }
        }
    }
    return result
}

private fun cacheAssetFile(context: Context, assetPath: String): File {
    val extension = assetPath.substringAfterLast('.', "bin")
    val digest = MessageDigest.getInstance("MD5")
        .digest(assetPath.toByteArray())
        .joinToString("") { byte -> "%02x".format(byte) }
    val directory = File(context.cacheDir, "asset_video_cache").apply { mkdirs() }
    val target = File(directory, "$digest.$extension")
    if (target.exists() && target.length() > 0L) {
        return target
    }

    synchronized(assetPath.intern()) {
        if (target.exists() && target.length() > 0L) {
            return target
        }
        context.assets.open(assetPath).use { input ->
            target.outputStream().use { output -> input.copyTo(output) }
        }
    }

    return target
}

private fun String.toColorOrFallback(): Color {
    return runCatching { Color(parseColor(this)) }.getOrDefault(Color(0xFF374151))
}
