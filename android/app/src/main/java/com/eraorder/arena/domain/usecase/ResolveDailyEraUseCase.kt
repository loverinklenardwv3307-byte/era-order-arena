package com.eraorder.arena.domain.usecase

import com.eraorder.arena.domain.model.HistoryEvent
import com.eraorder.arena.domain.repository.EventRepository
import kotlin.random.Random

class ResolveDailyEraUseCase(private val events: EventRepository) {

    operator fun invoke(epochDay: Long, exclude: Set<String>, count: Int): List<HistoryEvent> {
        val random = Random(epochDay * 7919L)
        val pool = events.allEvents().filter { it.id !in exclude }
        val source = if (pool.size >= count) pool else events.allEvents()
        return source.shuffled(random).take(count)
    }

    fun seedFor(epochDay: Long): Long = epochDay * 7919L
}
