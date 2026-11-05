package com.eraorder.arena.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.eraorder.arena.core.theme.Parchment
import com.eraorder.arena.core.theme.ParchmentDim

@Composable
fun SectionHeader(
    title: String,
    backLabel: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack, modifier = Modifier.size(48.dp)) {
            Icon(
                imageVector = AppIcons.ArrowBack,
                contentDescription = backLabel,
                tint = ParchmentDim,
                modifier = Modifier.size(24.dp)
            )
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = Parchment,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (trailing != null) {
            trailing()
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }
    }
}
