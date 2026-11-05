package com.eraorder.arena.core.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.ParchmentDim
import com.eraorder.arena.core.theme.WalnutChip
import com.eraorder.arena.core.theme.WalnutDeep

@Composable
fun SegmentedChips(
    options: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(WalnutChip.copy(alpha = 0.55f), RoundedCornerShape(12.dp))
            .border(BorderStroke(1.dp, Bronze.copy(alpha = 0.24f)), RoundedCornerShape(12.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        options.forEachIndexed { index, option ->
            val selected = index == selectedIndex
            val background by animateColorAsState(
                targetValue = if (selected) Bronze else WalnutChip.copy(alpha = 0f),
                label = "chipBackground"
            )
            val textColor by animateColorAsState(
                targetValue = if (selected) WalnutDeep else ParchmentDim,
                label = "chipText"
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 44.dp)
                    .background(background, RoundedCornerShape(9.dp))
                    .clickable { onSelect(index) }
                    .semantics { stateDescription = option }
                    .padding(horizontal = 6.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}
