package com.eraorder.arena.presentation.arena

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.eraorder.arena.R
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.LinkBlue
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.ParchmentDim
import com.eraorder.arena.core.theme.WalnutChip
import com.eraorder.arena.core.ui.PlaqueSurface
import com.eraorder.arena.core.ui.spriteResource

@Composable
fun ArenaCard(
    title: String,
    subtitle: String,
    spriteKey: String,
    chipLabel: String,
    secondChip: String,
    accent: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PlaqueSurface(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 104.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        borderColor = accent.copy(alpha = 0.32f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(spriteResource(spriteKey)),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(68.dp)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Parchment,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = ParchmentDim,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ArenaChip(text = chipLabel, color = Bronze)
                if (secondChip.isNotEmpty()) ArenaChip(text = secondChip, color = LinkBlue)
            }
        }
    }
}

@Composable
private fun ArenaChip(text: String, color: Color) {
    Box(
        modifier = Modifier
            .background(WalnutChip.copy(alpha = 0.85f), RoundedCornerShape(7.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.labelSmall, color = color, maxLines = 1)
    }
}

@Composable
fun arenaEventsChip(count: Int): String = stringResource(R.string.chip_events, count)

@Composable
fun arenaLearnedChip(best: Int): String =
    if (best > 0) stringResource(R.string.chip_learned, best) else ""
