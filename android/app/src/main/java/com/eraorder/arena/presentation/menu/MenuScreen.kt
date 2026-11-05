package com.eraorder.arena.presentation.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eraorder.arena.R
import com.eraorder.arena.core.di.AppViewModelFactory
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.ParchmentDim
import com.eraorder.arena.core.theme.Walnut
import com.eraorder.arena.core.theme.WalnutSurface
import com.eraorder.arena.core.ui.GalleryBackground
import com.eraorder.arena.core.ui.AppIcons

@Composable
fun MenuRoute(
    onPlay: () -> Unit,
    onTimeline: () -> Unit,
    onStats: () -> Unit,
    onSettings: () -> Unit,
    onDaily: () -> Unit
) {
    val menuViewModel: MenuViewModel = viewModel(factory = AppViewModelFactory())
    val state by menuViewModel.state.collectAsStateWithLifecycle()
    MenuScreen(
        state = state,
        onPlay = onPlay,
        onTimeline = onTimeline,
        onStats = onStats,
        onSettings = onSettings,
        onDaily = onDaily
    )
}

@Composable
private fun MenuScreen(
    state: MenuUiState,
    onPlay: () -> Unit,
    onTimeline: () -> Unit,
    onStats: () -> Unit,
    onSettings: () -> Unit,
    onDaily: () -> Unit
) {
    val drift by rememberInfiniteTransition(label = "menu").animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(tween(7000), RepeatMode.Reverse),
        label = "drift"
    )

    GalleryBackground(
        overlay = listOf(
            Walnut.copy(alpha = 0.45f),
            Color.Transparent,
            Walnut.copy(alpha = 0.92f)
        ),
        lampAlpha = 0.16f
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(WalnutSurface.copy(alpha = 0.82f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.icon_home),
                        contentDescription = stringResource(R.string.cd_gallery_home),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(onClick = onSettings, modifier = Modifier.size(48.dp)) {
                    Icon(
                        imageVector = AppIcons.Settings,
                        contentDescription = stringResource(R.string.cd_preferences),
                        tint = ParchmentDim,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(top = 78.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .background(
                                Brush.radialGradient(
                                    listOf(Bronze.copy(alpha = 0.22f), Color.Transparent)
                                ),
                                CircleShape
                            )
                    )
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(108.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.sprite_1),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .offset(x = (-18).dp, y = drift.dp)
                            .size(76.dp)
                            .alpha(0.85f)
                    )
                }
            }

            AnimatedVisibility(
                visible = state.loaded,
                enter = slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = spring(dampingRatio = 0.78f, stiffness = 220f)
                ) + fadeIn(animationSpec = tween(360)),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                MenuSheet(
                    state = state,
                    onPlay = onPlay,
                    onTimeline = onTimeline,
                    onStats = onStats,
                    onDaily = onDaily
                )
            }
        }
    }
}
