package com.eraorder.arena.presentation.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eraorder.arena.R
import com.eraorder.arena.core.di.AppViewModelFactory
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.LinkBlue
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.ParchmentDim
import com.eraorder.arena.core.theme.Walnut
import com.eraorder.arena.core.theme.WalnutDeep
import com.eraorder.arena.core.ui.GalleryBackground
import com.eraorder.arena.core.ui.eventDateText
import com.eraorder.arena.presentation.dialog.EventNoteDialog
import com.eraorder.arena.presentation.dialog.PauseDialog

@Composable
fun GameRoute(
    arenaId: String,
    daily: Boolean,
    sessionId: Long,
    onMenu: () -> Unit,
    onFinished: (Int, Int, Int, Boolean) -> Unit
) {
    val gameViewModel: GameViewModel = viewModel(
        key = "game-" + sessionId,
        factory = AppViewModelFactory(arenaId = arenaId, daily = daily)
    )
    val state by gameViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(gameViewModel) {
        gameViewModel.outcome.collect { result ->
            onFinished(result.placed, result.streak, result.lives, result.newRecord)
        }
    }
    DisposableEffect(gameViewModel) {
        onDispose { gameViewModel.pause() }
    }

    GameScreen(
        state = state,
        onPause = gameViewModel::pause,
        onResume = gameViewModel::resume,
        onEndRound = gameViewModel::endRoundEarly,
        onMenu = onMenu,
        onGapTap = gameViewModel::onGapTap,
        onGapBounds = gameViewModel::onGapBounds,
        onDragStart = gameViewModel::onDragStart,
        onDrag = gameViewModel::onDrag,
        onDragEnd = gameViewModel::onDragEnd,
        onDragCancel = gameViewModel::onDragCancel,
        onDismissNote = gameViewModel::dismissNote,
        onCause = gameViewModel::selectCause,
        onEffect = gameViewModel::selectEffect
    )
}

@Composable
private fun GameScreen(
    state: GameUiState,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onEndRound: () -> Unit,
    onMenu: () -> Unit,
    onGapTap: (Int) -> Unit,
    onGapBounds: (Int, Float, Float) -> Unit,
    onDragStart: (Float) -> Unit,
    onDrag: (Float, Float) -> Unit,
    onDragEnd: () -> Unit,
    onDragCancel: () -> Unit,
    onDismissNote: () -> Unit,
    onCause: (String) -> Unit,
    onEffect: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    val arenaLabel = if (state.daily) stringResource(R.string.arena_daily) else state.arenaName
    val roundLabel = stringResource(R.string.game_round, state.round, arenaLabel)
    val livesLabel = stringResource(R.string.cd_lives, state.lives)

    GalleryBackground(
        overlay = listOf(
            WalnutDeep.copy(alpha = 0.90f),
            Walnut.copy(alpha = 0.86f),
            WalnutDeep.copy(alpha = 0.94f)
        ),
        lampAlpha = 0.11f,
        cornerSprite = R.drawable.sprite_4,
        cornerAlpha = 0.08f
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onPause, modifier = Modifier.size(48.dp)) {
                    Image(
                        painter = painterResource(R.drawable.icon_pause),
                        contentDescription = stringResource(R.string.cd_pause),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Text(
                    text = roundLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = ParchmentDim,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 6.dp)
                )
                LivesStrip(lives = state.lives, description = livesLabel)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.game_placed, state.correctCount),
                    style = MaterialTheme.typography.labelSmall,
                    color = Bronze,
                    modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }
                )
                Text(
                    text = stringResource(R.string.game_streak, state.streak),
                    style = MaterialTheme.typography.labelSmall,
                    color = LinkBlue,
                    modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }
                )
            }

            Box(modifier = Modifier.fillMaxSize()) {
                if (state.phase == RoundPhase.CauseLinks) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        CauseLinkBoard(
                            causes = state.causes,
                            effects = state.effects,
                            selectedCauseId = state.selectedCauseId,
                            wrongEffectId = state.wrongEffectId,
                            enabled = state.interactive,
                            onCause = onCause,
                            onEffect = onEffect
                        )
                    }
                } else {
                    TimelineBody(
                        state = state,
                        scrollState = scrollState,
                        onGapTap = onGapTap,
                        onGapBounds = onGapBounds
                    )
                    FloatingCard(
                        state = state,
                        onDragStart = onDragStart,
                        onDrag = onDrag,
                        onDragEnd = onDragEnd,
                        onDragCancel = onDragCancel,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }

    val note = state.note
    if (note != null) {
        EventNoteDialog(
            title = note.title,
            dateLabel = eventDateText(note.year, state.dateFormat),
            placeLabel = note.place,
            body = note.note,
            correct = state.noteCorrect,
            onContinue = onDismissNote
        )
    }

    if (state.paused) {
        PauseDialog(
            roundLabel = roundLabel,
            onResume = onResume,
            onEndRound = onEndRound,
            onMenu = onMenu
        )
    }
}

@Composable
private fun TimelineBody(
    state: GameUiState,
    scrollState: androidx.compose.foundation.ScrollState,
    onGapTap: (Int) -> Unit,
    onGapBounds: (Int, Float, Float) -> Unit
) {
    val gapLabel = stringResource(R.string.cd_insert_here)
    val canPlace = state.current != null && state.interactive
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 140.dp)
    ) {
        state.timeline.forEachIndexed { index, item ->
            InsertionGap(
                index = index,
                active = canPlace && state.activeGap == index,
                enabled = canPlace,
                label = gapLabel,
                onTap = onGapTap,
                onBounds = onGapBounds
            )
            PlaqueRow(
                onLeft = index % 2 == 0,
                accent = if (item.correct) Bronze else LinkBlue
            ) {
                EventPlaque(
                    title = item.event.title,
                    dateLabel = eventDateText(item.event.year, state.dateFormat),
                    placeLabel = item.event.place,
                    correct = item.correct,
                    fresh = item.fresh,
                    largeText = state.largeText
                )
            }
        }
        InsertionGap(
            index = state.timeline.size,
            active = canPlace && state.activeGap == state.timeline.size,
            enabled = canPlace,
            label = gapLabel,
            onTap = onGapTap,
            onBounds = onGapBounds
        )
    }
}

@Composable
private fun FloatingCard(
    state: GameUiState,
    onDragStart: (Float) -> Unit,
    onDrag: (Float, Float) -> Unit,
    onDragEnd: () -> Unit,
    onDragCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val current = state.current
    Box(modifier = modifier.padding(horizontal = 18.dp, vertical = 24.dp)) {
        if (current != null) {
            DraggableEventCard(
                eventId = current.id,
                title = current.title,
                dragging = state.dragging,
                dragX = state.dragX,
                dragY = state.dragY,
                enabled = state.interactive,
                largeText = state.largeText,
                onDragStart = onDragStart,
                onDrag = onDrag,
                onDragEnd = onDragEnd,
                onDragCancel = onDragCancel
            )
        } else {
            Text(
                text = stringResource(
                    if (state.status == GameStatus.RoundComplete) {
                        R.string.game_round_complete
                    } else {
                        R.string.game_dealing
                    }
                ),
                style = MaterialTheme.typography.labelSmall,
                color = Parchment,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
