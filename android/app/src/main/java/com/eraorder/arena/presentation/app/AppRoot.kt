package com.eraorder.arena.presentation.app

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eraorder.arena.core.di.AppViewModelFactory
import com.eraorder.arena.core.navigation.Screen
import com.eraorder.arena.presentation.arena.ArenaSelectRoute
import com.eraorder.arena.presentation.game.GameRoute
import com.eraorder.arena.presentation.gameover.GameOverRoute
import com.eraorder.arena.presentation.menu.MenuRoute
import com.eraorder.arena.presentation.settings.ArenaPreferencesRoute
import com.eraorder.arena.presentation.splash.SplashRoute
import com.eraorder.arena.presentation.stats.LearnerStatsRoute
import com.eraorder.arena.presentation.timeline.PersonalTimelineRoute

@Composable
fun AppRoot() {
    val appViewModel: AppViewModel = viewModel(factory = AppViewModelFactory())
    val screen by appViewModel.screen.collectAsStateWithLifecycle()
    val canGoBack by appViewModel.canGoBack.collectAsStateWithLifecycle()

    BackHandler(enabled = canGoBack) { appViewModel.back() }

    AnimatedContent(
        targetState = screen,
        transitionSpec = {
            fadeIn(animationSpec = tween(420)) togetherWith fadeOut(animationSpec = tween(320))
        },
        label = "screen"
    ) { current ->
        Box(modifier = Modifier.fillMaxSize()) {
            when (current) {
                Screen.Splash -> SplashRoute(onFinished = { appViewModel.replace(Screen.Menu) })

                Screen.Menu -> MenuRoute(
                    onPlay = { appViewModel.navigate(Screen.ArenaSelect) },
                    onTimeline = { appViewModel.navigate(Screen.PersonalTimeline) },
                    onStats = { appViewModel.navigate(Screen.LearnerStats) },
                    onSettings = { appViewModel.navigate(Screen.ArenaPreferences) },
                    onDaily = { appViewModel.startGame(DAILY_ARENA, true) }
                )

                Screen.ArenaSelect -> ArenaSelectRoute(
                    onBack = { appViewModel.back() },
                    onArena = { arenaId, daily -> appViewModel.startGame(arenaId, daily) }
                )

                is Screen.Game -> GameRoute(
                    arenaId = current.arenaId,
                    daily = current.daily,
                    sessionId = current.sessionId,
                    onMenu = { appViewModel.openMenu() },
                    onFinished = { placed, streak, lives, record ->
                        appViewModel.finishSession(
                            Screen.GameOver(
                                arenaId = current.arenaId,
                                daily = current.daily,
                                placed = placed,
                                streak = streak,
                                lives = lives,
                                newRecord = record
                            )
                        )
                    }
                )

                is Screen.GameOver -> GameOverRoute(
                    arenaId = current.arenaId,
                    placed = current.placed,
                    streak = current.streak,
                    lives = current.lives,
                    newRecord = current.newRecord,
                    onPlayAgain = { appViewModel.restartGame(current.arenaId, current.daily) },
                    onMenu = { appViewModel.openMenu() }
                )

                Screen.PersonalTimeline -> PersonalTimelineRoute(
                    onBack = { appViewModel.back() },
                    onPlay = { appViewModel.navigate(Screen.ArenaSelect) }
                )

                Screen.LearnerStats -> LearnerStatsRoute(
                    onBack = { appViewModel.back() },
                    onPlay = { appViewModel.navigate(Screen.ArenaSelect) }
                )

                Screen.ArenaPreferences -> ArenaPreferencesRoute(onBack = { appViewModel.back() })
            }
        }
    }
}

private const val DAILY_ARENA = "daily"
