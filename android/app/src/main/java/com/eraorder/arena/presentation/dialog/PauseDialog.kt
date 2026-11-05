package com.eraorder.arena.presentation.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.eraorder.arena.R
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.ParchmentDim
import com.eraorder.arena.core.theme.WalnutSurface
import com.eraorder.arena.core.ui.PlaqueSurface
import com.eraorder.arena.core.ui.PrimaryButton
import com.eraorder.arena.core.ui.SecondaryButton
import com.eraorder.arena.core.ui.AppIcons

@Composable
fun PauseDialog(
    roundLabel: String,
    onResume: () -> Unit,
    onEndRound: () -> Unit,
    onMenu: () -> Unit
) {
    Dialog(onDismissRequest = onResume) {
        PlaqueSurface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            containerColor = WalnutSurface.copy(alpha = 0.98f),
            borderColor = Bronze.copy(alpha = 0.34f),
            elevation = 14.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.pause_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Parchment,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = roundLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = ParchmentDim,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                PrimaryButton(
                    text = stringResource(R.string.resume),
                    onClick = onResume,
                    icon = AppIcons.PlayArrow
                )
                Spacer(modifier = Modifier.height(10.dp))
                SecondaryButton(text = stringResource(R.string.end_round), onClick = onEndRound)
                Spacer(modifier = Modifier.height(10.dp))
                SecondaryButton(text = stringResource(R.string.menu), onClick = onMenu)
            }
        }
    }
}
