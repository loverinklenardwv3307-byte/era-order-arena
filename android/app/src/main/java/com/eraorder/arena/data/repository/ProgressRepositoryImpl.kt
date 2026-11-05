package com.eraorder.arena.data.repository

import com.eraorder.arena.data.local.PreferencesStore
import com.eraorder.arena.domain.model.LearnerProgress
import com.eraorder.arena.domain.model.Region
import com.eraorder.arena.domain.repository.ProgressRepository

class ProgressRepositoryImpl(private val store: PreferencesStore) : ProgressRepository {

    override fun load(): LearnerProgress = LearnerProgress(
        bestStreak = store.int(PreferencesStore.KEY_BEST_STREAK, 0),
        rounds = store.int(PreferencesStore.KEY_ROUNDS, 0),
        totalPlacements = store.int(PreferencesStore.KEY_TOTAL, 0),
        correctPlacements = store.int(PreferencesStore.KEY_CORRECT, 0),
        learnedIds = decodeIds(store.text(PreferencesStore.KEY_LEARNED, "")),
        centuryHits = decodeCenturies(store.text(PreferencesStore.KEY_CENTURIES, "")),
        regionHits = decodeRegions(store.text(PreferencesStore.KEY_REGIONS, ""))
    )

    override fun save(progress: LearnerProgress) {
        store.putInt(PreferencesStore.KEY_BEST_STREAK, progress.bestStreak)
        store.putInt(PreferencesStore.KEY_ROUNDS, progress.rounds)
        store.putInt(PreferencesStore.KEY_TOTAL, progress.totalPlacements)
        store.putInt(PreferencesStore.KEY_CORRECT, progress.correctPlacements)
        store.putText(PreferencesStore.KEY_LEARNED, progress.learnedIds.joinToString(","))
        store.putText(PreferencesStore.KEY_CENTURIES, encode(progress.centuryHits.mapKeys { it.key.toString() }))
        store.putText(PreferencesStore.KEY_REGIONS, encode(progress.regionHits.mapKeys { it.key.name }))
    }

    private fun decodeIds(raw: String): Set<String> =
        raw.split(",").filter { it.isNotBlank() }.toSet()

    private fun encode(map: Map<String, Pair<Int, Int>>): String =
        map.entries.joinToString(";") { entry ->
            entry.key + ":" + entry.value.first + ":" + entry.value.second
        }

    private fun decodePairs(raw: String): Map<String, Pair<Int, Int>> {
        val out = mutableMapOf<String, Pair<Int, Int>>()
        raw.split(";").filter { it.isNotBlank() }.forEach { chunk ->
            val parts = chunk.split(":")
            if (parts.size == 3) {
                val correct = parts[1].toIntOrNull()
                val total = parts[2].toIntOrNull()
                if (correct != null && total != null) out[parts[0]] = Pair(correct, total)
            }
        }
        return out
    }

    private fun decodeCenturies(raw: String): Map<Int, Pair<Int, Int>> {
        val out = mutableMapOf<Int, Pair<Int, Int>>()
        decodePairs(raw).forEach { (key, value) ->
            val century = key.toIntOrNull()
            if (century != null) out[century] = value
        }
        return out
    }

    private fun decodeRegions(raw: String): Map<Region, Pair<Int, Int>> {
        val out = mutableMapOf<Region, Pair<Int, Int>>()
        decodePairs(raw).forEach { (key, value) ->
            val region = Region.entries.firstOrNull { it.name == key }
            if (region != null) out[region] = value
        }
        return out
    }
}
