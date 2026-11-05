package com.eraorder.arena.core.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.WalnutChip

@Composable
fun AccuracyBar(
    label: String,
    valueLabel: String,
    percent: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    val fraction by animateFloatAsState(
        targetValue = (percent.coerceIn(0, 100)) / 100f,
        animationSpec = tween(durationMillis = 620),
        label = "accuracyFraction"
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .semantics { stateDescription = label + " " + valueLabel }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Parchment
            )
            Text(
                text = valueLabel,
                style = MaterialTheme.typography.labelSmall,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(WalnutChip, RoundedCornerShape(5.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .height(10.dp)
                    .background(color, RoundedCornerShape(5.dp))
            )
        }
    }
}
