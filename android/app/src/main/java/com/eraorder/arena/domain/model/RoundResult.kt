package com.eraorder.arena.domain.model

data class RoundResult(
    val arenaId: String,
    val daily: Boolean,
    val placed: Int,
    val correct: Int,
    val bestStreak: Int,
    val livesLeft: Int,
    val learnedIds: Set<String>,
    val centuryHits: Map<Int, Pair<Int, Int>>,
    val regionHits: Map<Region, Pair<Int, Int>>
)
