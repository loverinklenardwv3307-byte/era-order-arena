package com.eraorder.arena.domain.model

data class LearnerProgress(
    val bestStreak: Int = 0,
    val rounds: Int = 0,
    val totalPlacements: Int = 0,
    val correctPlacements: Int = 0,
    val learnedIds: Set<String> = emptySet(),
    val centuryHits: Map<Int, Pair<Int, Int>> = emptyMap(),
    val regionHits: Map<Region, Pair<Int, Int>> = emptyMap()
) {
    val eventsLearned: Int get() = learnedIds.size

    val accuracyPercent: Int
        get() = if (totalPlacements == 0) 0 else correctPlacements * 100 / totalPlacements
}
