package com.eraorder.arena.presentation.game

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.eraorder.arena.core.config.GameConfig
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.BronzeHighlight
import com.eraorder.arena.core.theme.LinkBlue
import com.eraorder.arena.core.ui.RailFill

@Composable
fun RailSegment(nodeVisible: Boolean, accent: Color, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .width(4.dp)
                .fillMaxHeight()
                .background(RailFill)
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = (-2).dp)
                .width(1.dp)
                .fillMaxHeight()
                .background(BronzeHighlight.copy(alpha = 0.4f))
        )
        if (nodeVisible) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(12.dp)
                    .background(accent, CircleShape)
            )
        }
    }
}

@Composable
fun InsertionGap(
    index: Int,
    active: Boolean,
    enabled: Boolean,
    label: String,
    onTap: (Int) -> Unit,
    onBounds: (Int, Float, Float) -> Unit
) {
    val height by animateDpAsState(
        targetValue = if (active) 92.dp else 36.dp,
        animationSpec = tween(durationMillis = GameConfig.GAP_MS, easing = FastOutSlowInEasing),
        label = "gapHeight"
    )
    val glow by animateFloatAsState(
        targetValue = if (active) 0.30f else 0f,
        animationSpec = tween(durationMillis = GameConfig.GAP_MS),
        label = "gapGlow"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .onGloballyPositioned { coordinates ->
                val top = coordinates.positionInRoot().y
                onBounds(index, top, top + coordinates.size.height)
            }
            .then(
                if (enabled) {
                    Modifier
                        .clickable { onTap(index) }
                        .semantics { contentDescription = label }
                } else {
                    Modifier
                }
            )
    ) {
        Box(modifier = Modifier.matchParentSize()) {
            RailSegment(
                nodeVisible = false,
                accent = Bronze,
                modifier = Modifier.fillMaxSize()
            )
            if (glow > 0f) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .height(height - 8.dp)
                        .padding(horizontal = 18.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    LinkBlue.copy(alpha = glow),
                                    Color.Transparent
                                )
                            ),
                            RoundedCornerShape(12.dp)
                        )
                )
            }
        }
    }
}

@Composable
fun PlaqueRow(onLeft: Boolean, accent: Color, plaque: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.matchParentSize()) {
            RailSegment(
                nodeVisible = true,
                accent = accent,
                modifier = Modifier.fillMaxSize()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onLeft) {
                Box(modifier = Modifier.weight(1f)) { plaque() }
                Spacer(modifier = Modifier.weight(1f))
            } else {
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.weight(1f)) { plaque() }
            }
        }
    }
}
