package com.eraorder.arena.presentation.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eraorder.arena.domain.repository.EventRepository
import com.eraorder.arena.domain.repository.PreferencesRepository
import com.eraorder.arena.domain.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonalTimelineViewModel(
    private val events: EventRepository,
    private val progress: ProgressRepository,
    private val preferences: PreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PersonalTimelineUiState())
    val state: StateFlow<PersonalTimelineUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val stored = progress.load()
            val prefs = preferences.load()
            val learned = events.allEvents()
                .filter { it.id in stored.learnedIds }
                .sortedBy { it.year }
            val groups = learned
                .groupBy { it.century }
                .toSortedMap()
                .map { entry -> CenturyGroup(century = entry.key, events = entry.value) }
            _state.update {
                it.copy(
                    groups = groups,
                    learnedCount = learned.size,
                    dateFormat = prefs.dateFormat,
                    loaded = true
                )
            }
        }
    }
}
