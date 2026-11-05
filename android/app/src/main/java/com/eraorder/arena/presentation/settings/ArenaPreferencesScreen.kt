package com.eraorder.arena.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eraorder.arena.R
import com.eraorder.arena.core.di.AppViewModelFactory
import com.eraorder.arena.core.theme.Bronze
import com.eraorder.arena.core.theme.LinkBlue
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.ParchmentDim
import com.eraorder.arena.core.theme.ParchmentMuted
import com.eraorder.arena.core.theme.Walnut
import com.eraorder.arena.core.theme.WalnutChip
import com.eraorder.arena.core.theme.WalnutDeep
import com.eraorder.arena.core.ui.GalleryBackground
import com.eraorder.arena.core.ui.PlaqueSurface
import com.eraorder.arena.core.ui.SectionHeader
import com.eraorder.arena.core.ui.SegmentedChips
import com.eraorder.arena.core.ui.eventDateText

@Composable
fun ArenaPreferencesRoute(onBack: () -> Unit) {
    val preferencesViewModel: ArenaPreferencesViewModel = viewModel(factory = AppViewModelFactory())
    val state by preferencesViewModel.state.collectAsStateWithLifecycle()
    ArenaPreferencesScreen(
        state = state,
        onBack = onBack,
        onDateFormat = preferencesViewModel::selectDateFormat,
        onTextSize = preferencesViewModel::selectTextSize,
        onHaptics = preferencesViewModel::setHaptics,
        onSound = preferencesViewModel::setSound
    )
}

@Composable
private fun ArenaPreferencesScreen(
    state: ArenaPreferencesUiState,
    onBack: () -> Unit,
    onDateFormat: (Int) -> Unit,
    onTextSize: (Int) -> Unit,
    onHaptics: (Boolean) -> Unit,
    onSound: (Boolean) -> Unit
) {
    val scrollState = rememberScrollState()
    GalleryBackground(
        overlay = listOf(
            WalnutDeep.copy(alpha = 0.90f),
            Walnut.copy(alpha = 0.88f),
            WalnutDeep.copy(alpha = 0.94f)
        ),
        lampAlpha = 0.10f,
        cornerSprite = R.drawable.sprite_5,
        cornerAlpha = 0.07f
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            SectionHeader(
                title = stringResource(R.string.prefs_title),
                backLabel = stringResource(R.string.cd_back),
                onBack = onBack
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                PreferenceBlock(label = stringResource(R.string.prefs_date_format)) {
                    SegmentedChips(
                        options = listOf(
                            stringResource(R.string.option_bce_ce),
                            stringResource(R.string.option_bc_ad)
                        ),
                        selectedIndex = state.dateFormatIndex,
                        onSelect = onDateFormat
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.prefs_preview),
                            style = MaterialTheme.typography.labelSmall,
                            color = ParchmentMuted
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            DatePlate(text = eventDateText(-776, state.dateFormat))
                            DatePlate(text = eventDateText(1492, state.dateFormat))
                        }
                    }
                }

                PreferenceBlock(label = stringResource(R.string.prefs_text_size)) {
                    SegmentedChips(
                        options = listOf(
                            stringResource(R.string.option_text_standard),
                            stringResource(R.string.option_text_large)
                        ),
                        selectedIndex = state.textSizeIndex,
                        onSelect = onTextSize
                    )
                }

                PreferenceBlock(label = stringResource(R.string.prefs_haptics)) {
                    ToggleRow(
                        label = stringResource(R.string.prefs_haptics),
                        checked = state.haptics,
                        onChange = onHaptics
                    )
                }

                PreferenceBlock(label = stringResource(R.string.prefs_sound)) {
                    ToggleRow(
                        label = stringResource(R.string.prefs_sound),
                        checked = state.sound,
                        onChange = onSound
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun PreferenceBlock(label: String, content: @Composable () -> Unit) {
    PlaqueSurface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Bronze
            )
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
private fun ToggleRow(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    val stateLabel = if (checked) {
        stringResource(R.string.prefs_on)
    } else {
        stringResource(R.string.prefs_off)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .semantics { stateDescription = label + " " + stateLabel },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stateLabel,
            style = MaterialTheme.typography.labelMedium,
            color = if (checked) Parchment else ParchmentMuted
        )
        Switch(
            checked = checked,
            onCheckedChange = onChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = WalnutDeep,
                checkedTrackColor = Bronze,
                checkedBorderColor = Bronze,
                uncheckedThumbColor = ParchmentDim,
                uncheckedTrackColor = WalnutChip,
                uncheckedBorderColor = ParchmentMuted
            )
        )
    }
}

@Composable
private fun DatePlate(text: String) {
    PlaqueSurface(
        shape = RoundedCornerShape(8.dp),
        borderColor = LinkBlue.copy(alpha = 0.34f),
        elevation = 3.dp
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = Bronze,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}
