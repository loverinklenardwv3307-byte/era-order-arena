package com.eraorder.arena.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.BronzeDeep
import com.eraorder.arena.core.theme.BronzeHighlight
import com.eraorder.arena.core.theme.BronzeLight
import com.eraorder.arena.core.theme.WalnutChip
import com.eraorder.arena.core.theme.WalnutDeep
import com.eraorder.arena.core.theme.WalnutRaised

val BronzeFill = Brush.horizontalGradient(listOf(BronzeDeep, Bronze, BronzeLight))

val DisabledFill = Brush.horizontalGradient(listOf(WalnutChip, WalnutChip))

val RailFill = Brush.verticalGradient(listOf(BronzeDeep, Bronze, BronzeDeep))

@Composable
fun PlaqueSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    containerColor: Color = WalnutRaised.copy(alpha = 0.94f),
    borderColor: Color = Bronze.copy(alpha = 0.30f),
    elevation: Dp = 6.dp,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.shadow(elevation = elevation, shape = shape, ambientColor = Bronze),
        shape = shape,
        color = containerColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Box {
            content()
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(BronzeHighlight.copy(alpha = 0.18f))
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(WalnutDeep.copy(alpha = 0.5f))
            )
        }
    }
}
