package com.eraorder.arena.presentation.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.eraorder.arena.core.theme.BronzeLight
import com.eraorder.arena.core.theme.LinkBlue
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.ParchmentDim
import com.eraorder.arena.core.theme.Walnut
import com.eraorder.arena.core.theme.WalnutDeep
import com.eraorder.arena.core.ui.AccuracyBar
import com.eraorder.arena.core.ui.GalleryBackground
import com.eraorder.arena.core.ui.PlaqueSurface
import com.eraorder.arena.core.ui.PrimaryButton
import com.eraorder.arena.core.ui.SectionHeader
import com.eraorder.arena.core.ui.StatCard
import com.eraorder.arena.core.ui.regionLabel
import com.eraorder.arena.core.ui.AppIcons

@Composable
fun LearnerStatsRoute(onBack: () -> Unit, onPlay: () -> Unit) {
    val statsViewModel: LearnerStatsViewModel = viewModel(factory = AppViewModelFactory())
    val state by statsViewModel.state.collectAsStateWithLifecycle()
    LearnerStatsScreen(state = state, onBack = onBack, onPlay = onPlay)
}

@Composable
private fun LearnerStatsScreen(
    state: LearnerStatsUiState,
    onBack: () -> Unit,
    onPlay: () -> Unit
) {
    val scrollState = rememberScrollState()
    GalleryBackground(
        overlay = listOf(
            WalnutDeep.copy(alpha = 0.88f),
            Walnut.copy(alpha = 0.86f),
            WalnutDeep.copy(alpha = 0.94f)
        ),
        lampAlpha = 0.12f,
        cornerSprite = R.drawable.sprite_1,
        cornerAlpha = 0.07f
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            SectionHeader(
                title = stringResource(R.string.stats_title),
                backLabel = stringResource(R.string.cd_back),
                onBack = onBack
            )
            if (!state.hasData) {
                EmptyWall(onPlay = onPlay)
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (state.showProgressRow) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics { liveRegion = LiveRegionMode.Polite },
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            StatCard(
                                icon = AppIcons.Star,
                                value = state.bestStreak.toString(),
                                label = stringResource(R.string.stat_best_streak),
                                valueColor = Bronze,
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                icon = AppIcons.CheckCircle,
                                value = state.eventsLearned.toString(),
                                label = stringResource(R.string.stat_events_learned),
                                valueColor = LinkBlue,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    if (state.showSessionRow) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            StatCard(
                                icon = AppIcons.DateRange,
                                value = stringResource(R.string.stat_percent, state.accuracy),
                                label = stringResource(R.string.stat_accuracy),
                                valueColor = BronzeLight,
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                icon = AppIcons.Refresh,
                                value = state.rounds.toString(),
                                label = stringResource(R.string.stat_rounds),
                                valueColor = Parchment,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    if (state.regions.isNotEmpty()) {
                        RegionWall(regions = state.regions)
                    }
                    PrimaryButton(text = stringResource(R.string.play), onClick = onPlay)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun RegionWall(regions: List<RegionAccuracy>) {
    PlaqueSurface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
            Text(
                text = stringResource(R.string.stats_regions),
                style = MaterialTheme.typography.labelLarge,
                color = Bronze
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .width(26.dp)
                    .height(2.dp)
                    .background(Bronze, RoundedCornerShape(1.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            regions.forEachIndexed { index, item ->
                if (index > 0) Spacer(modifier = Modifier.height(14.dp))
                AccuracyBar(
                    label = regionLabel(item.region),
                    valueLabel = stringResource(R.string.stat_percent, item.percent),
                    percent = item.percent,
                    color = if (item.percent >= 80) LinkBlue else Bronze
                )
            }
        }
    }
}

@Composable
private fun EmptyWall(onPlay: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.sprite_3),
            contentDescription = stringResource(R.string.cd_arena_sprite),
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(96.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.stats_empty),
            style = MaterialTheme.typography.labelSmall,
            color = ParchmentDim,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        PrimaryButton(text = stringResource(R.string.play), onClick = onPlay)
    }
}
