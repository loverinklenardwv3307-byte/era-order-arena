package com.eraorder.arena.domain.usecase

class ValidatePlacementUseCase {

    fun isCorrect(timelineYears: List<Int>, gapIndex: Int, year: Int): Boolean {
        if (gapIndex < 0 || gapIndex > timelineYears.size) return false
        val lower = if (gapIndex == 0) Int.MIN_VALUE else timelineYears[gapIndex - 1]
        val upper = if (gapIndex == timelineYears.size) Int.MAX_VALUE else timelineYears[gapIndex]
        return year in lower..upper
    }

    fun trueIndex(timelineYears: List<Int>, year: Int): Int =
        timelineYears.count { it <= year }
}
