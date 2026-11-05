package com.eraorder.arena.core.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.ParchmentMuted
import com.eraorder.arena.core.theme.WalnutChip

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    leadingPainter: Painter? = null,
    enabled: Boolean = true
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed && enabled) 0.96f else 1f,
        animationSpec = spring(dampingRatio = 0.45f, stiffness = 650f),
        label = "secondaryScale"
    )
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        interactionSource = interaction,
        shape = RoundedCornerShape(13.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) Bronze.copy(alpha = 0.30f) else WalnutChip
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = WalnutChip.copy(alpha = 0.72f),
            contentColor = Parchment,
            disabledContainerColor = WalnutChip.copy(alpha = 0.35f),
            disabledContentColor = ParchmentMuted
        ),
        contentPadding = PaddingValues(horizontal = 10.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .scale(scale)
    ) {
        if (leadingPainter != null) {
            Icon(
                painter = leadingPainter,
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        } else if (icon != null) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text = text, style = MaterialTheme.typography.labelMedium, maxLines = 1)
    }
}
