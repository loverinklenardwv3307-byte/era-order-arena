package com.eraorder.arena.domain.usecase

import com.eraorder.arena.domain.model.HistoryEvent
import com.eraorder.arena.domain.repository.EventRepository
import kotlin.random.Random

class DealRoundUseCase(private val events: EventRepository) {

    operator fun invoke(
        arenaId: String,
        exclude: Set<String>,
        count: Int,
        seed: Long?
    ): List<HistoryEvent> {
        val pool = events.events(arenaId).filter { it.id !in exclude }
        val source = if (pool.size >= count) pool else events.events(arenaId)
        val random = if (seed == null) Random(Random.nextLong()) else Random(seed)
        return source.shuffled(random).take(count)
    }
}
