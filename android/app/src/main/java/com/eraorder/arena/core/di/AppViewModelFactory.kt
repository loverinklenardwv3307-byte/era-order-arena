package com.eraorder.arena.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.eraorder.arena.presentation.app.AppViewModel
import com.eraorder.arena.presentation.arena.ArenaSelectViewModel
import com.eraorder.arena.presentation.game.GameViewModel
import com.eraorder.arena.presentation.gameover.GameOverViewModel
import com.eraorder.arena.presentation.menu.MenuViewModel
import com.eraorder.arena.presentation.settings.ArenaPreferencesViewModel
import com.eraorder.arena.presentation.splash.SplashViewModel
import com.eraorder.arena.presentation.stats.LearnerStatsViewModel
import com.eraorder.arena.presentation.timeline.PersonalTimelineViewModel

class AppViewModelFactory(
    private val arenaId: String = "",
    private val daily: Boolean = false,
    private val placed: Int = 0,
    private val streak: Int = 0,
    private val lives: Int = 0
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val created: ViewModel = when {
            modelClass.isAssignableFrom(AppViewModel::class.java) -> AppViewModel()
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel()
            modelClass.isAssignableFrom(MenuViewModel::class.java) ->
                MenuViewModel(ServiceLocator.progressRepository)

            modelClass.isAssignableFrom(ArenaSelectViewModel::class.java) ->
                ArenaSelectViewModel(ServiceLocator.eventRepository, ServiceLocator.progressRepository)

            modelClass.isAssignableFrom(GameViewModel::class.java) -> GameViewModel(
                arenaId = arenaId,
                daily = daily,
                events = ServiceLocator.eventRepository,
                preferences = ServiceLocator.preferencesRepository,
                dealRound = ServiceLocator.dealRound,
                validate = ServiceLocator.validatePlacement,
                resolveDaily = ServiceLocator.resolveDailyEra,
                updateProgress = ServiceLocator.updateProgress
            )

            modelClass.isAssignableFrom(GameOverViewModel::class.java) -> GameOverViewModel(
                progress = ServiceLocator.progressRepository,
                events = ServiceLocator.eventRepository,
                arenaId = arenaId,
                placed = placed,
                streak = streak,
                lives = lives
            )

            modelClass.isAssignableFrom(PersonalTimelineViewModel::class.java) ->
                PersonalTimelineViewModel(
                    ServiceLocator.eventRepository,
                    ServiceLocator.progressRepository,
                    ServiceLocator.preferencesRepository
                )

            modelClass.isAssignableFrom(LearnerStatsViewModel::class.java) ->
                LearnerStatsViewModel(ServiceLocator.progressRepository)

            modelClass.isAssignableFrom(ArenaPreferencesViewModel::class.java) ->
                ArenaPreferencesViewModel(ServiceLocator.preferencesRepository)

            else -> throw IllegalArgumentException(modelClass.name)
        }
        return created as T
    }
}
