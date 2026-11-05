package com.eraorder.arena.presentation.game

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.eraorder.arena.core.config.GameConfig
import com.eraorder.arena.core.theme.Ember
import com.eraorder.arena.core.theme.WalnutChip
import com.eraorder.arena.core.ui.AppIcons

@Composable
fun LivesStrip(lives: Int, description: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.semantics {
            liveRegion = LiveRegionMode.Polite
            stateDescription = description
        },
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        repeat(GameConfig.LIVES) { index ->
            val alive = index < lives
            val pop by animateFloatAsState(
                targetValue = if (alive) 1f else 0.82f,
                animationSpec = spring(dampingRatio = 0.4f, stiffness = 700f),
                label = "lifePop"
            )
            Icon(
                imageVector = if (alive) AppIcons.Favorite else AppIcons.FavoriteBorder,
                contentDescription = null,
                tint = if (alive) Ember else WalnutChip,
                modifier = Modifier
                    .size(18.dp)
                    .scale(pop)
            )
        }
    }
}
