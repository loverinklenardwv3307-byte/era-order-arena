package com.eraorder.arena.presentation.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eraorder.arena.R
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.LinkBlue
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.ParchmentDim
import com.eraorder.arena.core.theme.WalnutChip
import com.eraorder.arena.core.theme.WalnutSurface
import com.eraorder.arena.core.ui.PrimaryButton
import com.eraorder.arena.core.ui.SecondaryButton
import com.eraorder.arena.core.ui.StatCard
import com.eraorder.arena.core.ui.AppIcons

@Composable
fun MenuSheet(
    state: MenuUiState,
    onPlay: () -> Unit,
    onTimeline: () -> Unit,
    onStats: () -> Unit,
    onDaily: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        color = WalnutSurface.copy(alpha = 0.95f),
        tonalElevation = 8.dp,
        border = BorderStroke(1.dp, Bronze.copy(alpha = 0.28f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp, top = 12.dp, bottom = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(width = 46.dp, height = 4.dp)
                    .background(Bronze.copy(alpha = 0.5f), RoundedCornerShape(2.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.splash_title),
                style = MaterialTheme.typography.headlineMedium,
                color = Parchment,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.menu_tagline),
                style = MaterialTheme.typography.bodyMedium,
                color = ParchmentDim,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (state.hasHistory) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                        icon = AppIcons.Check,
                        value = state.eventsLearned.toString(),
                        label = stringResource(R.string.stat_events_learned),
                        valueColor = LinkBlue,
                        modifier = Modifier.weight(1f)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(WalnutChip.copy(alpha = 0.55f), RoundedCornerShape(12.dp))
                        .padding(vertical = 14.dp, horizontal = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.menu_first_hint),
                        style = MaterialTheme.typography.labelSmall,
                        color = ParchmentDim,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            PrimaryButton(
                text = stringResource(R.string.play),
                onClick = onPlay,
                icon = AppIcons.PlayArrow
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SecondaryButton(
                    text = stringResource(R.string.menu_timeline),
                    onClick = onTimeline,
                    modifier = Modifier.weight(1f)
                )
                SecondaryButton(
                    text = stringResource(R.string.menu_stats),
                    onClick = onStats,
                    modifier = Modifier.weight(1f)
                )
                SecondaryButton(
                    text = stringResource(R.string.menu_daily),
                    onClick = onDaily,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
