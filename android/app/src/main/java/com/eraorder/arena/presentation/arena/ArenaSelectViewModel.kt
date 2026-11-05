package com.eraorder.arena.presentation.arena

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eraorder.arena.domain.repository.EventRepository
import com.eraorder.arena.domain.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArenaSelectViewModel(
    private val events: EventRepository,
    private val progress: ProgressRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ArenaSelectUiState())
    val state: StateFlow<ArenaSelectUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val stored = progress.load()
            val rows = events.arenas().map { arena ->
                ArenaRow(
                    arena = arena,
                    bestStreak = stored.bestStreak,
                    learnedHere = events.events(arena.id).count { it.id in stored.learnedIds }
                )
            }
            _state.update { it.copy(rows = rows) }
        }
    }

    fun openPrimer(arenaId: String, daily: Boolean) {
        viewModelScope.launch {
            val arena = events.arena(arenaId)
            _state.update {
                it.copy(
                    primerArenaId = arena.id,
                    primerTitle = arena.name,
                    primerRange = arena.eraRange,
                    primerText = arena.primer,
                    primerDaily = daily
                )
            }
        }
    }

    fun dismissPrimer() {
        _state.update { it.copy(primerArenaId = "") }
    }
}
