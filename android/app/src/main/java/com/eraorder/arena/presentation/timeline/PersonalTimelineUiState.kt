package com.eraorder.arena.presentation.timeline

import com.eraorder.arena.domain.model.DateFormat
import com.eraorder.arena.domain.model.HistoryEvent

data class CenturyGroup(
    val century: Int,
    val events: List<HistoryEvent>
)

data class PersonalTimelineUiState(
    val groups: List<CenturyGroup> = emptyList(),
    val learnedCount: Int = 0,
    val dateFormat: DateFormat = DateFormat.BCE_CE,
    val loaded: Boolean = false
) {
    val hasData: Boolean get() = groups.isNotEmpty()
}
