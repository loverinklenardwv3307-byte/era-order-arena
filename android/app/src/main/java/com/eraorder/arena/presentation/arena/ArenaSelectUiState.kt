package com.eraorder.arena.presentation.arena

import com.eraorder.arena.domain.model.Arena

data class ArenaRow(
    val arena: Arena,
    val bestStreak: Int,
    val learnedHere: Int
)

data class ArenaSelectUiState(
    val rows: List<ArenaRow> = emptyList(),
    val dailyOpened: Boolean = false,
    val primerArenaId: String = "",
    val primerTitle: String = "",
    val primerRange: String = "",
    val primerText: String = "",
    val primerDaily: Boolean = false
) {
    val primerVisible: Boolean get() = primerArenaId.isNotEmpty()
}
