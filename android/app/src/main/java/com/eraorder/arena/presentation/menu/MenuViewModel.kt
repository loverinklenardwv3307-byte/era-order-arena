package com.eraorder.arena.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eraorder.arena.domain.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(private val progress: ProgressRepository) : ViewModel() {

    private val _state = MutableStateFlow(MenuUiState())
    val state: StateFlow<MenuUiState> = _state.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            val stored = progress.load()
            _state.update {
                it.copy(
                    bestStreak = stored.bestStreak,
                    eventsLearned = stored.eventsLearned,
                    rounds = stored.rounds,
                    loaded = true
                )
            }
        }
    }
}
