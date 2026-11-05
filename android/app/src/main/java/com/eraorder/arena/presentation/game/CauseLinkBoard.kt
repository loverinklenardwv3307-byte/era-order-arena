package com.eraorder.arena.presentation.game

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.eraorder.arena.R
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.Ember
import com.eraorder.arena.core.theme.LinkBlue
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.ParchmentDim
import com.eraorder.arena.core.theme.WalnutRaised

@Composable
fun CauseLinkBoard(
    causes: List<LinkOption>,
    effects: List<LinkOption>,
    selectedCauseId: String,
    wrongEffectId: String,
    enabled: Boolean,
    onCause: (String) -> Unit,
    onEffect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(R.string.game_links_hint),
            style = MaterialTheme.typography.labelSmall,
            color = ParchmentDim,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.game_causes),
                    style = MaterialTheme.typography.labelSmall,
                    color = Bronze
                )
                causes.forEach { option ->
                    LinkTile(
                        label = option.label,
                        accent = Bronze,
                        selected = option.id == selectedCauseId,
                        linked = option.linked,
                        wrong = false,
                        enabled = enabled && !option.linked,
                        onClick = { onCause(option.id) }
                    )
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = stringResource(R.string.game_effects),
                    style = MaterialTheme.typography.labelSmall,
                    color = LinkBlue
                )
                effects.forEach { option ->
                    LinkTile(
                        label = option.label,
                        accent = LinkBlue,
                        selected = false,
                        linked = option.linked,
                        wrong = option.id == wrongEffectId,
                        enabled = enabled && !option.linked && selectedCauseId.isNotEmpty(),
                        onClick = { onEffect(option.id) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun LinkTile(
    label: String,
    accent: Color,
    selected: Boolean,
    linked: Boolean,
    wrong: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val border by animateColorAsState(
        targetValue = when {
            wrong -> Ember
            linked -> accent
            selected -> accent
            else -> accent.copy(alpha = 0.26f)
        },
        label = "linkBorder"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 68.dp)
            .background(
                if (linked) accent.copy(alpha = 0.16f) else WalnutRaised.copy(alpha = 0.92f),
                RoundedCornerShape(12.dp)
            )
            .border(width = if (selected) 2.dp else 1.dp, color = border, shape = RoundedCornerShape(12.dp))
            .clickable(enabled = enabled) { onClick() }
            .padding(horizontal = 10.dp, vertical = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = if (enabled || linked) Parchment else ParchmentDim,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}
