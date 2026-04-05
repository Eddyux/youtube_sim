package com.example.youtube_sim.view.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.ui.AspectRatioFrameLayout
import com.example.youtube_sim.model.FeedItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShortsScreen(
    modifier: Modifier = Modifier,
    items: List<FeedItem>
) {
    if (items.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No shorts yet", style = MaterialTheme.typography.titleMedium)
        }
        return
    }

    val pagerState = rememberPagerState(pageCount = { items.size })

    VerticalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize(),
        beyondViewportPageCount = 1
    ) { page ->
        val item = items[page]
        val active = pagerState.currentPage == page
        ShortsPage(item = item, active = active)
    }
}

@Composable
private fun ShortsPage(
    item: FeedItem,
    active: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AssetVideoPlayer(
            item = item,
            modifier = Modifier.fillMaxSize(),
            playWhenReady = active,
            repeatMode = Player.REPEAT_MODE_ONE,
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0x22000000),
                            Color.Transparent,
                            Color(0x66000000),
                            Color(0xBB000000)
                        )
                    )
                )
        )

        Text(
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopCenter)
                .padding(top = 12.dp),
            text = "Shorts",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .navigationBarsPadding()
                .padding(start = 16.dp, end = 96.dp, bottom = 28.dp)
        ) {
            Text(
                text = item.creator,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = item.title,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            item.actionText?.let { duration ->
                Spacer(modifier = Modifier.size(10.dp))
                Surface(shape = CircleShape, color = Color(0x44000000)) {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        text = duration,
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(end = 14.dp, bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShortsAction(buttonText = "Like", label = "1.2K")
            ShortsAction(buttonText = "Talk", label = "86")
            ShortsAction(buttonText = "Send", label = "Share")
        }
    }
}

@Composable
private fun ShortsAction(
    buttonText: String,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier.size(60.dp),
            shape = CircleShape,
            color = Color(0x44000000)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = buttonText,
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.size(6.dp))
        Text(text = label, color = Color.White, style = MaterialTheme.typography.labelMedium)
    }
}
