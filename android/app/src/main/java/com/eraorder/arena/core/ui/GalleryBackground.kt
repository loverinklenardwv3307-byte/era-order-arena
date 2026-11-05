package com.eraorder.arena.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.eraorder.arena.R
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.Walnut

@Composable
fun GalleryBackground(
    overlay: List<Color>,
    modifier: Modifier = Modifier,
    lampAlpha: Float = 0.12f,
    cornerSprite: Int = 0,
    cornerAlpha: Float = 0.10f,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier.fillMaxSize().background(Walnut)) {
        Image(
            painter = painterResource(R.drawable.bg_game),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(overlay))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(Bronze.copy(alpha = lampAlpha), Color.Transparent),
                        radius = 900f
                    )
                )
        )
        if (cornerSprite != 0) {
            Image(
                painter = painterResource(cornerSprite),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 26.dp, y = 54.dp)
                    .size(150.dp)
                    .alpha(cornerAlpha)
            )
            Image(
                painter = painterResource(cornerSprite),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-30).dp, y = 24.dp)
                    .size(170.dp)
                    .alpha(cornerAlpha * 0.8f)
            )
        }
        content()
    }
}
