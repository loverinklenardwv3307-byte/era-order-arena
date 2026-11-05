package com.eraorder.arena.domain.usecase

import com.eraorder.arena.domain.model.LearnerProgress
import com.eraorder.arena.domain.model.Region
import com.eraorder.arena.domain.model.RoundResult
import com.eraorder.arena.domain.repository.ProgressRepository
import kotlin.math.max

class UpdateProgressUseCase(private val progress: ProgressRepository) {

    operator fun invoke(result: RoundResult): LearnerProgress {
        val stored = progress.load()
        val centuries = merge(stored.centuryHits, result.centuryHits)
        val regions = mergeRegions(stored.regionHits, result.regionHits)
        val updated = stored.copy(
            bestStreak = max(stored.bestStreak, result.bestStreak),
            rounds = stored.rounds + 1,
            totalPlacements = stored.totalPlacements + result.placed,
            correctPlacements = stored.correctPlacements + result.correct,
            learnedIds = stored.learnedIds + result.learnedIds,
            centuryHits = centuries,
            regionHits = regions
        )
        progress.save(updated)
        return updated
    }

    fun isNewRecord(streak: Int): Boolean = streak > progress.load().bestStreak

    private fun merge(
        base: Map<Int, Pair<Int, Int>>,
        extra: Map<Int, Pair<Int, Int>>
    ): Map<Int, Pair<Int, Int>> {
        val out = base.toMutableMap()
        extra.forEach { (key, value) ->
            val current = out[key] ?: Pair(0, 0)
            out[key] = Pair(current.first + value.first, current.second + value.second)
        }
        return out
    }

    private fun mergeRegions(
        base: Map<Region, Pair<Int, Int>>,
        extra: Map<Region, Pair<Int, Int>>
    ): Map<Region, Pair<Int, Int>> {
        val out = base.toMutableMap()
        extra.forEach { (key, value) ->
            val current = out[key] ?: Pair(0, 0)
            out[key] = Pair(current.first + value.first, current.second + value.second)
        }
        return out
    }
}
