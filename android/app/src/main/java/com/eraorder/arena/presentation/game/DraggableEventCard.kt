package com.eraorder.arena.presentation.game

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.eraorder.arena.R
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.ParchmentDim
import com.eraorder.arena.core.theme.WalnutSurface
import com.eraorder.arena.core.ui.PlaqueSurface
import kotlin.math.roundToInt

@Composable
fun DraggableEventCard(
    eventId: String,
    title: String,
    dragging: Boolean,
    dragX: Float,
    dragY: Float,
    enabled: Boolean,
    largeText: Boolean,
    onDragStart: (Float) -> Unit,
    onDrag: (Float, Float) -> Unit,
    onDragEnd: () -> Unit,
    onDragCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var anchor by remember(eventId) { mutableStateOf(0f) }
    val breathe by rememberInfiniteTransition(label = "card").animateFloat(
        initialValue = 1f,
        targetValue = 1.015f,
        animationSpec = infiniteRepeatable(tween(2600), RepeatMode.Reverse),
        label = "breathe"
    )
    val cardLabel = stringResource(R.string.cd_drag_card)
    PlaqueSurface(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 94.dp)
            .onGloballyPositioned { anchor = it.positionInRoot().y }
            .offset { IntOffset(dragX.roundToInt(), dragY.roundToInt()) }
            .zIndex(if (dragging) 1f else 0f)
            .scale(if (dragging) 1.04f else breathe)
            .semantics { contentDescription = cardLabel }
            .pointerInput(eventId, enabled) {
                if (!enabled) return@pointerInput
                detectDragGestures(
                    onDragStart = { offset -> onDragStart(anchor + offset.y) },
                    onDrag = { change, amount ->
                        change.consume()
                        onDrag(amount.x, amount.y)
                    },
                    onDragEnd = { onDragEnd() },
                    onDragCancel = { onDragCancel() }
                )
            },
        shape = RoundedCornerShape(20.dp),
        containerColor = WalnutSurface.copy(alpha = 0.96f),
        borderColor = Bronze.copy(alpha = 0.34f),
        elevation = if (dragging) 16.dp else 12.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = if (largeText) 17.sp else 15.sp,
                    color = Parchment,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.game_where),
                    style = MaterialTheme.typography.labelSmall,
                    color = ParchmentDim
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.size(36.dp).padding(top = 10.dp)
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .width(22.dp)
                            .height(2.dp)
                            .background(Bronze.copy(alpha = 0.7f), RoundedCornerShape(1.dp))
                    )
                }
            }
        }
    }
}
