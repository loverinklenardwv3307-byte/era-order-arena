package com.eraorder.arena.presentation.menu

data class MenuUiState(
    val bestStreak: Int = 0,
    val eventsLearned: Int = 0,
    val rounds: Int = 0,
    val loaded: Boolean = false
) {
    val hasHistory: Boolean get() = bestStreak > 0 && eventsLearned > 0
}
