package com.eraorder.arena.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eraorder.arena.core.config.GameConfig
import com.eraorder.arena.domain.model.HistoryEvent
import com.eraorder.arena.domain.model.Region
import com.eraorder.arena.domain.model.RoundResult
import com.eraorder.arena.domain.model.TextSize
import com.eraorder.arena.domain.repository.EventRepository
import com.eraorder.arena.domain.repository.PreferencesRepository
import com.eraorder.arena.domain.usecase.DealRoundUseCase
import com.eraorder.arena.domain.usecase.ResolveDailyEraUseCase
import com.eraorder.arena.domain.usecase.UpdateProgressUseCase
import com.eraorder.arena.domain.usecase.ValidatePlacementUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max

data class SessionOutcome(
    val placed: Int,
    val streak: Int,
    val lives: Int,
    val newRecord: Boolean
)

class GameViewModel(
    private val arenaId: String,
    private val daily: Boolean,
    private val events: EventRepository,
    private val preferences: PreferencesRepository,
    private val dealRound: DealRoundUseCase,
    private val validate: ValidatePlacementUseCase,
    private val resolveDaily: ResolveDailyEraUseCase,
    private val updateProgress: UpdateProgressUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GameUiState())
    val state: StateFlow<GameUiState> = _state.asStateFlow()

    private val _outcome = Channel<SessionOutcome>(Channel.BUFFERED)
    val outcome = _outcome.receiveAsFlow()

    private val gapBounds = mutableMapOf<Int, ClosedFloatingPointRange<Float>>()
    private val queue = ArrayDeque<HistoryEvent>()
    private val used = mutableSetOf<String>()
    private val learned = mutableSetOf<String>()
    private val centuryHits = mutableMapOf<Int, Pair<Int, Int>>()
    private val regionHits = mutableMapOf<Region, Pair<Int, Int>>()
    private val linkAnswers = mutableMapOf<String, String>()
    private var bestStreak = 0
    private var dragAnchorY = 0f
    private var roundJob: Job? = null

    init {
        viewModelScope.launch {
            val prefs = preferences.load()
            _state.update {
                it.copy(
                    arenaName = events.arena(arenaId).name,
                    daily = daily,
                    dateFormat = prefs.dateFormat,
                    largeText = prefs.textSize == TextSize.LARGE
                )
            }
            startRound(1)
        }
    }

    fun pause() {
        if (_state.value.status == GameStatus.Finished) return
        _state.update { it.copy(paused = true) }
    }

    fun resume() {
        _state.update { it.copy(paused = false) }
    }

    fun onGapBounds(index: Int, top: Float, bottom: Float) {
        if (bottom > top) gapBounds[index] = top..bottom
    }

    fun onDragStart(anchorY: Float) {
        if (!_state.value.interactive) return
        dragAnchorY = anchorY
        _state.update { it.copy(dragging = true, status = GameStatus.Dragging) }
    }

    fun onDrag(deltaX: Float, deltaY: Float) {
        val current = _state.value
        if (!current.dragging) return
        val x = current.dragX + deltaX
        val y = current.dragY + deltaY
        _state.update { it.copy(dragX = x, dragY = y, activeGap = gapAt(dragAnchorY + y)) }
    }

    fun onDragEnd() {
        val current = _state.value
        if (!current.dragging) return
        val gap = current.activeGap
        _state.update { it.copy(dragging = false, dragX = 0f, dragY = 0f) }
        if (gap in 0 until current.gapCount) place(gap) else cancelDrag()
    }

    fun onDragCancel() {
        cancelDrag()
    }

    fun onGapTap(index: Int) {
        if (!_state.value.interactive) return
        place(index)
    }

    fun endRoundEarly() {
        _state.update { it.copy(paused = false) }
        finishSession()
    }

    fun dismissNote() {
        val current = _state.value
        _state.update { it.copy(note = null, status = GameStatus.Dealing) }
        if (current.lives <= 0) {
            finishSession()
        } else {
            viewModelScope.launch {
                delay(GameConfig.DEAL_MS)
                dealNext()
            }
        }
    }

    fun selectCause(id: String) {
        if (!_state.value.interactive) return
        _state.update { it.copy(selectedCauseId = id, wrongEffectId = "") }
    }

    fun selectEffect(id: String) {
        val current = _state.value
        if (!current.interactive || current.selectedCauseId.isEmpty()) return
        if (linkAnswers[current.selectedCauseId] == id) {
            val streak = current.streak + 1
            bestStreak = max(bestStreak, streak)
            _state.update { base ->
                base.copy(
                    causes = base.causes.map {
                        if (it.id == base.selectedCauseId) it.copy(linked = true) else it
                    },
                    effects = base.effects.map { if (it.id == id) it.copy(linked = true) else it },
                    selectedCauseId = "",
                    wrongEffectId = "",
                    streak = streak,
                    placedCount = base.placedCount + 1,
                    correctCount = base.correctCount + 1
                )
            }
            if (_state.value.causes.all { it.linked }) completeRound()
        } else {
            val lives = current.lives - 1
            _state.update {
                it.copy(
                    lives = lives,
                    streak = 0,
                    wrongEffectId = id,
                    placedCount = it.placedCount + 1
                )
            }
            if (lives <= 0) finishSession()
        }
    }

    private fun cancelDrag() {
        _state.update {
            it.copy(
                dragging = false,
                dragX = 0f,
                dragY = 0f,
                activeGap = -1,
                status = if (it.status == GameStatus.Dragging) GameStatus.Idle else it.status
            )
        }
    }

    private fun gapAt(pointerY: Float): Int {
        if (gapBounds.isEmpty()) return -1
        gapBounds.entries.firstOrNull { pointerY in it.value }?.let { return it.key }
        return gapBounds.entries.minByOrNull { entry ->
            abs((entry.value.start + entry.value.endInclusive) / 2f - pointerY)
        }?.key ?: -1
    }

    private fun record(event: HistoryEvent, correct: Boolean) {
        val century = centuryHits[event.century] ?: Pair(0, 0)
        centuryHits[event.century] = Pair(century.first + if (correct) 1 else 0, century.second + 1)
        val region = regionHits[event.region] ?: Pair(0, 0)
        regionHits[event.region] = Pair(region.first + if (correct) 1 else 0, region.second + 1)
    }

    private fun place(gapIndex: Int) {
        val current = _state.value
        val event = current.current ?: return
        val years = current.timeline.map { it.event.year }
        val correct = validate.isCorrect(years, gapIndex, event.year)
        val target = validate.trueIndex(years, event.year)
        val timeline = current.timeline.map { it.copy(fresh = false) }.toMutableList()
        timeline.add(target, PlacedEvent(event, correct, true))
        val streak = if (correct) current.streak + 1 else 0
        bestStreak = max(bestStreak, streak)
        if (correct) learned.add(event.id)
        record(event, correct)
        val lives = if (correct) current.lives else current.lives - 1
        _state.update {
            it.copy(
                timeline = timeline,
                current = null,
                activeGap = -1,
                dragging = false,
                dragX = 0f,
                dragY = 0f,
                streak = streak,
                lives = lives,
                placedCount = it.placedCount + 1,
                correctCount = if (correct) it.correctCount + 1 else it.correctCount,
                status = GameStatus.Resolving
            )
        }
        viewModelScope.launch {
            delay(if (correct) GameConfig.ENGRAVE_MS.toLong() else GameConfig.GLIDE_MS.toLong())
            _state.update { it.copy(status = GameStatus.Noting, note = event, noteCorrect = correct) }
        }
    }

    private fun dealNext() {
        val next = queue.removeFirstOrNull()
        if (next == null) {
            completeRound()
        } else {
            _state.update { it.copy(current = next, status = GameStatus.Idle) }
        }
    }

    private fun completeRound() {
        _state.update { it.copy(status = GameStatus.RoundComplete) }
        roundJob?.cancel()
        roundJob = viewModelScope.launch {
            delay(GameConfig.ROUND_SWEEP_MS)
            startRound(_state.value.round + 1)
        }
    }

    private fun startRound(round: Int) {
        if (round % GameConfig.CAUSE_LINK_EVERY == 0) {
            startCauseRound(round)
        } else {
            startTimelineRound(round)
        }
    }

    private fun startTimelineRound(round: Int) {
        val epochDay = System.currentTimeMillis() / GameConfig.MS_PER_DAY
        val dealt = if (daily) {
            resolveDaily(epochDay, used, GameConfig.EVENTS_PER_ROUND + 1)
        } else {
            dealRound(arenaId, used, GameConfig.EVENTS_PER_ROUND + 1, null)
        }
        if (dealt.isEmpty()) {
            finishSession()
            return
        }
        dealt.forEach { used.add(it.id) }
        queue.clear()
        val anchor = dealt.first()
        dealt.drop(1).forEach { queue.addLast(it) }
        val existing = _state.value.timeline.map { it.copy(fresh = false) }
        val seeded = (existing + PlacedEvent(anchor, true, false)).sortedBy { it.event.year }
        learned.add(anchor.id)
        _state.update {
            it.copy(
                round = round,
                phase = RoundPhase.Timeline,
                timeline = seeded,
                causes = emptyList(),
                effects = emptyList(),
                status = GameStatus.Dealing
            )
        }
        viewModelScope.launch {
            delay(GameConfig.DEAL_MS)
            dealNext()
        }
    }

    private fun startCauseRound(round: Int) {
        val pairs = events.causePairs(arenaId).shuffled().take(GameConfig.CAUSE_PAIRS_PER_ROUND)
        if (pairs.size < 2) {
            startTimelineRound(round)
            return
        }
        linkAnswers.clear()
        pairs.forEach { linkAnswers[it.id] = it.id }
        _state.update {
            it.copy(
                round = round,
                phase = RoundPhase.CauseLinks,
                current = null,
                causes = pairs.map { pair -> LinkOption(pair.id, pair.cause, false) },
                effects = pairs.shuffled().map { pair -> LinkOption(pair.id, pair.effect, false) },
                selectedCauseId = "",
                wrongEffectId = "",
                status = GameStatus.Idle
            )
        }
    }

    private fun finishSession() {
        if (_state.value.status == GameStatus.Finished) return
        roundJob?.cancel()
        val current = _state.value
        _state.update { it.copy(status = GameStatus.Finished, note = null, paused = false) }
        viewModelScope.launch {
            val record = updateProgress.isNewRecord(bestStreak)
            updateProgress(
                RoundResult(
                    arenaId = arenaId,
                    daily = daily,
                    placed = current.placedCount,
                    correct = current.correctCount,
                    bestStreak = bestStreak,
                    livesLeft = current.lives,
                    learnedIds = learned.toSet(),
                    centuryHits = centuryHits.toMap(),
                    regionHits = regionHits.toMap()
                )
            )
            _outcome.send(
                SessionOutcome(
                    placed = current.correctCount,
                    streak = bestStreak,
                    lives = current.lives.coerceAtLeast(0),
                    newRecord = record && bestStreak > 0
                )
            )
        }
    }
}
