package com.eraorder.arena.domain.model

import kotlin.math.abs

enum class Region { EUROPE, ASIA, AFRICA, AMERICAS, OCEANIA }

data class HistoryEvent(
    val id: String,
    val arenaId: String,
    val title: String,
    val year: Int,
    val place: String,
    val region: Region,
    val note: String
) {
    val century: Int
        get() {
            val value = (abs(year) - 1) / 100 + 1
            return if (year < 0) -value else value
        }
}
