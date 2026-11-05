package com.eraorder.arena.presentation.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eraorder.arena.R
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.Ember
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.ParchmentDim
import com.eraorder.arena.core.theme.WalnutSurface
import com.eraorder.arena.core.ui.PlaqueSurface
import com.eraorder.arena.core.ui.PrimaryButton
import com.eraorder.arena.core.ui.AppIcons

@Composable
fun EventNoteDialog(
    title: String,
    dateLabel: String,
    placeLabel: String,
    body: String,
    correct: Boolean,
    onContinue: () -> Unit
) {
    val accent = if (correct) Bronze else Ember
    Dialog(onDismissRequest = onContinue) {
        PlaqueSurface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            containerColor = WalnutSurface.copy(alpha = 0.98f),
            borderColor = accent.copy(alpha = 0.40f),
            elevation = 14.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
                    .semantics { liveRegion = LiveRegionMode.Polite },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(accent.copy(alpha = 0.16f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (correct) AppIcons.Check else AppIcons.Clear,
                        contentDescription = stringResource(
                            if (correct) R.string.note_correct else R.string.note_wrong
                        ),
                        tint = accent,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Parchment,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = dateLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = accent
                    )
                    Text(
                        text = placeLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = ParchmentDim
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = ParchmentDim,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                PrimaryButton(text = stringResource(R.string.continue_label), onClick = onContinue)
            }
        }
    }
}
