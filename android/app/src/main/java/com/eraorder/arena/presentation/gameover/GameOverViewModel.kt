package com.eraorder.arena.presentation.gameover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eraorder.arena.domain.repository.EventRepository
import com.eraorder.arena.domain.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GameOverUiState(
    val arenaName: String = "",
    val placed: Int = 0,
    val streak: Int = 0,
    val lives: Int = 0,
    val bestStreak: Int = 0,
    val eventsLearned: Int = 0,
    val survived: Boolean = false
)

class GameOverViewModel(
    private val progress: ProgressRepository,
    private val events: EventRepository,
    private val arenaId: String,
    private val placed: Int,
    private val streak: Int,
    private val lives: Int
) : ViewModel() {

    private val _state = MutableStateFlow(GameOverUiState())
    val state: StateFlow<GameOverUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val stored = progress.load()
            _state.update {
                it.copy(
                    arenaName = events.arena(arenaId).name,
                    placed = placed,
                    streak = streak,
                    lives = lives,
                    bestStreak = stored.bestStreak,
                    eventsLearned = stored.eventsLearned,
                    survived = lives > 0
                )
            }
        }
    }
}
