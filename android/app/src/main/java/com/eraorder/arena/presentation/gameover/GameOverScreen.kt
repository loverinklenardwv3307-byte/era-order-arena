package com.eraorder.arena.presentation.gameover

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eraorder.arena.R
import com.eraorder.arena.core.di.AppViewModelFactory
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.Ember
import com.eraorder.arena.core.theme.LinkBlue
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.ParchmentDim
import com.eraorder.arena.core.theme.Walnut
import com.eraorder.arena.core.theme.WalnutDeep
import com.eraorder.arena.core.ui.GalleryBackground
import com.eraorder.arena.core.ui.PrimaryButton
import com.eraorder.arena.core.ui.SecondaryButton
import com.eraorder.arena.core.ui.StatCard
import com.eraorder.arena.core.ui.AppIcons

@Composable
fun GameOverRoute(
    arenaId: String,
    placed: Int,
    streak: Int,
    lives: Int,
    newRecord: Boolean,
    onPlayAgain: () -> Unit,
    onMenu: () -> Unit
) {
    val gameOverViewModel: GameOverViewModel = viewModel(
        key = "gameover-" + arenaId + "-" + placed + "-" + streak + "-" + lives,
        factory = AppViewModelFactory(
            arenaId = arenaId,
            placed = placed,
            streak = streak,
            lives = lives
        )
    )
    val state by gameOverViewModel.state.collectAsStateWithLifecycle()
    GameOverScreen(
        state = state,
        newRecord = newRecord,
        onPlayAgain = onPlayAgain,
        onMenu = onMenu
    )
}

@Composable
private fun GameOverScreen(
    state: GameOverUiState,
    newRecord: Boolean,
    onPlayAgain: () -> Unit,
    onMenu: () -> Unit
) {
    val visibleCards = listOf(state.placed > 0, state.streak > 0, state.lives > 0).count { it }
    GalleryBackground(
        overlay = listOf(
            Walnut.copy(alpha = 0.78f),
            WalnutDeep.copy(alpha = 0.88f),
            WalnutDeep.copy(alpha = 0.94f)
        ),
        lampAlpha = 0.14f
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.sprite_5),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(92.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(
                    if (state.survived) R.string.result_complete else R.string.result_out_of_lives
                ),
                style = MaterialTheme.typography.headlineMedium,
                color = if (state.survived) Bronze else Ember,
                textAlign = TextAlign.Center,
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = state.arenaName,
                style = MaterialTheme.typography.labelSmall,
                color = ParchmentDim,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            AnimatedVisibility(
                visible = newRecord,
                enter = slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(320)
                ) + fadeIn(animationSpec = tween(320))
            ) {
                Box(
                    modifier = Modifier
                        .background(LinkBlue.copy(alpha = 0.18f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.result_new_record),
                        style = MaterialTheme.typography.labelSmall,
                        color = LinkBlue
                    )
                }
            }
            if (visibleCards >= 2) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (state.placed > 0) {
                        StatCard(
                            icon = AppIcons.Star,
                            value = state.placed.toString(),
                            label = stringResource(R.string.stat_placed),
                            valueColor = Bronze,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (state.streak > 0) {
                        StatCard(
                            icon = AppIcons.Refresh,
                            value = state.streak.toString(),
                            label = stringResource(R.string.stat_streak),
                            valueColor = LinkBlue,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (state.lives > 0) {
                        StatCard(
                            icon = AppIcons.Favorite,
                            value = state.lives.toString(),
                            label = stringResource(R.string.stat_lives),
                            valueColor = Parchment,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(26.dp))
            PrimaryButton(
                text = stringResource(R.string.play_again),
                onClick = onPlayAgain,
                icon = AppIcons.Refresh
            )
            Spacer(modifier = Modifier.height(12.dp))
            SecondaryButton(
                text = stringResource(R.string.menu),
                onClick = onMenu,
                leadingPainter = painterResource(R.drawable.icon_home)
            )
        }
    }
}
