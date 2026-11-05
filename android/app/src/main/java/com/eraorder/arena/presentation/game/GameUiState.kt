package com.eraorder.arena.presentation.game

import com.eraorder.arena.core.config.GameConfig
import com.eraorder.arena.domain.model.DateFormat
import com.eraorder.arena.domain.model.HistoryEvent

enum class GameStatus { Dealing, Idle, Dragging, Resolving, Noting, RoundComplete, Finished }

enum class RoundPhase { Timeline, CauseLinks }

data class PlacedEvent(
    val event: HistoryEvent,
    val correct: Boolean,
    val fresh: Boolean
)

data class LinkOption(
    val id: String,
    val label: String,
    val linked: Boolean
)

data class GameUiState(
    val arenaName: String = "",
    val daily: Boolean = false,
    val round: Int = 1,
    val lives: Int = GameConfig.LIVES,
    val streak: Int = 0,
    val placedCount: Int = 0,
    val correctCount: Int = 0,
    val status: GameStatus = GameStatus.Dealing,
    val phase: RoundPhase = RoundPhase.Timeline,
    val timeline: List<PlacedEvent> = emptyList(),
    val current: HistoryEvent? = null,
    val activeGap: Int = -1,
    val dragging: Boolean = false,
    val dragX: Float = 0f,
    val dragY: Float = 0f,
    val note: HistoryEvent? = null,
    val noteCorrect: Boolean = true,
    val paused: Boolean = false,
    val causes: List<LinkOption> = emptyList(),
    val effects: List<LinkOption> = emptyList(),
    val selectedCauseId: String = "",
    val wrongEffectId: String = "",
    val dateFormat: DateFormat = DateFormat.BCE_CE,
    val largeText: Boolean = false,
    val newRecord: Boolean = false
) {
    val interactive: Boolean
        get() = status == GameStatus.Idle || status == GameStatus.Dragging

    val gapCount: Int get() = timeline.size + 1
}
