package com.eraorder.arena.core.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.BronzeHighlight
import com.eraorder.arena.core.theme.ParchmentMuted
import com.eraorder.arena.core.theme.WalnutChip
import com.eraorder.arena.core.theme.WalnutDeep

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed && enabled) 0.96f else 1f,
        animationSpec = spring(dampingRatio = 0.45f, stiffness = 650f),
        label = "primaryScale"
    )
    val shape = RoundedCornerShape(16.dp)
    Button(
        onClick = onClick,
        enabled = enabled,
        interactionSource = interaction,
        shape = shape,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = WalnutDeep,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = ParchmentMuted
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) BronzeHighlight.copy(alpha = 0.35f) else WalnutChip
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .scale(scale)
            .shadow(elevation = if (enabled) 10.dp else 0.dp, shape = shape, ambientColor = Bronze)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = if (enabled) BronzeFill else DisabledFill, shape = shape),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Text(text = text, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
