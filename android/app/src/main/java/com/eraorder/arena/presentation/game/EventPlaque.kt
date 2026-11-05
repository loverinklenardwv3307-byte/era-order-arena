package com.eraorder.arena.presentation.game

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eraorder.arena.core.config.GameConfig
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.Ember
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.ParchmentDim
import com.eraorder.arena.core.theme.WalnutRaised
import com.eraorder.arena.core.ui.PlaqueSurface

@Composable
fun EventPlaque(
    title: String,
    dateLabel: String,
    placeLabel: String,
    correct: Boolean,
    fresh: Boolean,
    largeText: Boolean,
    modifier: Modifier = Modifier
) {
    val engrave by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = GameConfig.ENGRAVE_MS),
        label = "engrave"
    )
    val accent = if (correct) Bronze else Ember
    PlaqueSurface(
        modifier = modifier.heightIn(min = 80.dp),
        shape = RoundedCornerShape(14.dp),
        containerColor = WalnutRaised.copy(alpha = 0.96f),
        borderColor = if (fresh) accent.copy(alpha = 0.72f) else accent.copy(alpha = 0.34f),
        elevation = if (fresh) 10.dp else 5.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 11.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = if (largeText) 15.sp else 13.sp,
                color = Parchment,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(modifier = Modifier.alpha(if (fresh) engrave else 1f)) {
                Text(
                    text = dateLabel,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = if (largeText) 15.sp else 13.sp,
                    color = accent
                )
            }
            Text(
                text = placeLabel,
                style = MaterialTheme.typography.labelSmall,
                color = ParchmentDim,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
