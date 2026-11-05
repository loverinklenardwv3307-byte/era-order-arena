package com.eraorder.arena.presentation.settings

import com.eraorder.arena.domain.model.DateFormat
import com.eraorder.arena.domain.model.TextSize

data class ArenaPreferencesUiState(
    val dateFormat: DateFormat = DateFormat.BCE_CE,
    val textSize: TextSize = TextSize.STANDARD,
    val haptics: Boolean = true,
    val sound: Boolean = true,
    val loaded: Boolean = false
) {
    val dateFormatIndex: Int get() = if (dateFormat == DateFormat.BCE_CE) 0 else 1

    val textSizeIndex: Int get() = if (textSize == TextSize.STANDARD) 0 else 1
}
