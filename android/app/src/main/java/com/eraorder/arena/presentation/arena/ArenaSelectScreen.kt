package com.eraorder.arena.presentation.arena

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eraorder.arena.R
import com.eraorder.arena.core.di.AppViewModelFactory
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.LinkBlue
import com.eraorder.arena.core.theme.Walnut
import com.eraorder.arena.core.theme.WalnutDeep
import com.eraorder.arena.core.ui.GalleryBackground
import com.eraorder.arena.core.ui.SectionHeader
import com.eraorder.arena.presentation.dialog.EraPrimerDialog

@Composable
fun ArenaSelectRoute(onBack: () -> Unit, onArena: (String, Boolean) -> Unit) {
    val arenaViewModel: ArenaSelectViewModel = viewModel(factory = AppViewModelFactory())
    val state by arenaViewModel.state.collectAsStateWithLifecycle()
    ArenaSelectScreen(
        state = state,
        onBack = onBack,
        onOpenPrimer = arenaViewModel::openPrimer,
        onDismissPrimer = arenaViewModel::dismissPrimer,
        onBegin = onArena
    )
}

@Composable
private fun ArenaSelectScreen(
    state: ArenaSelectUiState,
    onBack: () -> Unit,
    onOpenPrimer: (String, Boolean) -> Unit,
    onDismissPrimer: () -> Unit,
    onBegin: (String, Boolean) -> Unit
) {
    GalleryBackground(
        overlay = listOf(
            WalnutDeep.copy(alpha = 0.88f),
            Walnut.copy(alpha = 0.86f),
            WalnutDeep.copy(alpha = 0.92f)
        ),
        lampAlpha = 0.10f,
        cornerSprite = R.drawable.sprite_2,
        cornerAlpha = 0.07f
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            SectionHeader(
                title = stringResource(R.string.arena_title),
                backLabel = stringResource(R.string.cd_back),
                onBack = onBack
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    ArenaCard(
                        title = stringResource(R.string.arena_daily),
                        subtitle = stringResource(R.string.arena_daily_subtitle),
                        spriteKey = "sprite_3",
                        chipLabel = stringResource(R.string.chip_daily),
                        secondChip = "",
                        accent = LinkBlue,
                        onClick = { onOpenPrimer(state.rows.firstOrNull()?.arena?.id.orEmpty(), true) }
                    )
                }
                items(state.rows, key = { it.arena.id }) { row ->
                    ArenaCard(
                        title = row.arena.name,
                        subtitle = row.arena.eraRange,
                        spriteKey = row.arena.spriteKey,
                        chipLabel = arenaEventsChip(row.arena.eventCount),
                        secondChip = arenaLearnedChip(row.learnedHere),
                        accent = Bronze,
                        onClick = { onOpenPrimer(row.arena.id, false) }
                    )
                }
            }
        }
    }

    if (state.primerVisible) {
        EraPrimerDialog(
            title = state.primerTitle,
            eraRange = state.primerRange,
            body = state.primerText,
            daily = state.primerDaily,
            onBegin = {
                onDismissPrimer()
                onBegin(state.primerArenaId, state.primerDaily)
            },
            onDismiss = onDismissPrimer
        )
    }
}
