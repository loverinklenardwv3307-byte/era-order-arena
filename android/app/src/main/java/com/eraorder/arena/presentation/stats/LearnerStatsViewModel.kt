package com.eraorder.arena.presentation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eraorder.arena.domain.model.Region
import com.eraorder.arena.domain.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LearnerStatsViewModel(private val progress: ProgressRepository) : ViewModel() {

    private val _state = MutableStateFlow(LearnerStatsUiState())
    val state: StateFlow<LearnerStatsUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val stored = progress.load()
            val regions = Region.entries.mapNotNull { region ->
                val hits = stored.regionHits[region]
                if (hits == null || hits.second == 0) {
                    null
                } else {
                    RegionAccuracy(region = region, correct = hits.first, total = hits.second)
                }
            }.sortedByDescending { it.percent }
            _state.update {
                it.copy(
                    bestStreak = stored.bestStreak,
                    eventsLearned = stored.eventsLearned,
                    rounds = stored.rounds,
                    accuracy = stored.accuracyPercent,
                    regions = regions,
                    loaded = true
                )
            }
        }
    }
}
