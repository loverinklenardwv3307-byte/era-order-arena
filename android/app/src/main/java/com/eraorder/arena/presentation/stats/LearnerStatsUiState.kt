package com.eraorder.arena.presentation.stats

import com.eraorder.arena.domain.model.Region

data class RegionAccuracy(
    val region: Region,
    val correct: Int,
    val total: Int
) {
    val percent: Int get() = if (total == 0) 0 else correct * 100 / total
}

data class LearnerStatsUiState(
    val bestStreak: Int = 0,
    val eventsLearned: Int = 0,
    val rounds: Int = 0,
    val accuracy: Int = 0,
    val regions: List<RegionAccuracy> = emptyList(),
    val loaded: Boolean = false
) {
    val hasData: Boolean get() = rounds > 0

    val showProgressRow: Boolean get() = bestStreak > 0 && eventsLearned > 0

    val showSessionRow: Boolean get() = accuracy > 0 && rounds > 0
}
