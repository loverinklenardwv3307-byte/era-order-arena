package com.eraorder.arena.presentation.timeline

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import com.eraorder.arena.core.theme.WalnutDeep
import com.eraorder.arena.core.ui.GalleryBackground
import com.eraorder.arena.core.ui.PlaqueSurface
import com.eraorder.arena.core.ui.PrimaryButton
import com.eraorder.arena.core.ui.SectionHeader
import com.eraorder.arena.core.ui.centuryText
import com.eraorder.arena.core.ui.eventDateText
import com.eraorder.arena.domain.model.DateFormat
import com.eraorder.arena.domain.model.HistoryEvent

@Composable
fun PersonalTimelineRoute(onBack: () -> Unit, onPlay: () -> Unit) {
    val timelineViewModel: PersonalTimelineViewModel = viewModel(factory = AppViewModelFactory())
    val state by timelineViewModel.state.collectAsStateWithLifecycle()
    PersonalTimelineScreen(state = state, onBack = onBack, onPlay = onPlay)
}

@Composable
private fun PersonalTimelineScreen(
    state: PersonalTimelineUiState,
    onBack: () -> Unit,
    onPlay: () -> Unit
) {
    GalleryBackground(
        overlay = listOf(
            WalnutDeep.copy(alpha = 0.86f),
            Walnut.copy(alpha = 0.88f),
            WalnutDeep.copy(alpha = 0.95f)
        ),
        lampAlpha = 0.13f,
        cornerSprite = R.drawable.sprite_2,
        cornerAlpha = 0.07f
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            SectionHeader(
                title = stringResource(R.string.timeline_title),
                backLabel = stringResource(R.string.cd_back),
                onBack = onBack
            )
            if (!state.hasData) {
                EmptyRail(onPlay = onPlay)
            } else {
                Text(
                    text = stringResource(R.string.timeline_count, state.learnedCount),
                    style = MaterialTheme.typography.labelSmall,
                    color = ParchmentMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    items(state.groups, key = { it.century }) { group ->
                        CenturySection(group = group, dateFormat = state.dateFormat)
                    }
                }
            }
        }
    }
}

@Composable
private fun CenturySection(group: CenturyGroup, dateFormat: DateFormat) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = centuryText(group.century, dateFormat),
                style = MaterialTheme.typography.labelLarge,
                color = Bronze
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(Bronze.copy(alpha = 0.28f))
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(group.events, key = { it.id }) { event ->
                TimelineTick(event = event, dateFormat = dateFormat)
            }
        }
    }
}

@Composable
private fun TimelineTick(event: HistoryEvent, dateFormat: DateFormat) {
    val dateLabel = eventDateText(event.year, dateFormat)
    PlaqueSurface(
        modifier = Modifier
            .width(196.dp)
            .semantics { stateDescription = dateLabel },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(96.dp)
                    .background(Bronze.copy(alpha = 0.85f))
            )
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)) {
                Text(
                    text = dateLabel,
                    style = MaterialTheme.typography.titleSmall,
                    color = LinkBlue
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Parchment,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = event.place,
                    style = MaterialTheme.typography.labelSmall,
                    color = ParchmentMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun EmptyRail(onPlay: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.sprite_4),
            contentDescription = stringResource(R.string.cd_arena_sprite),
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(96.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.timeline_empty),
            style = MaterialTheme.typography.labelSmall,
            color = ParchmentDim,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        PrimaryButton(text = stringResource(R.string.play), onClick = onPlay)
    }
}
