package com.eraorder.arena.domain.model

enum class DateFormat { BCE_CE, BC_AD }

enum class TextSize { STANDARD, LARGE }

data class AppPreferences(
    val dateFormat: DateFormat = DateFormat.BCE_CE,
    val textSize: TextSize = TextSize.STANDARD,
    val haptics: Boolean = true,
    val sound: Boolean = true
)
