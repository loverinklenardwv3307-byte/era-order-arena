package com.eraorder.arena.data.repository

import com.eraorder.arena.data.sample.SampleData
import com.eraorder.arena.domain.model.Arena
import com.eraorder.arena.domain.model.CausePair
import com.eraorder.arena.domain.model.HistoryEvent
import com.eraorder.arena.domain.repository.EventRepository

class EventRepositoryImpl : EventRepository {

    private val byArena: Map<String, List<HistoryEvent>> =
        SampleData.events.groupBy { it.arenaId }

    override fun arenas(): List<Arena> = SampleData.arenas

    override fun arena(arenaId: String): Arena =
        SampleData.arenas.firstOrNull { it.id == arenaId } ?: SampleData.arenas.first()

    override fun events(arenaId: String): List<HistoryEvent> = byArena[arenaId].orEmpty()

    override fun allEvents(): List<HistoryEvent> = SampleData.events

    override fun causePairs(arenaId: String): List<CausePair> {
        val own = SampleData.causePairs.filter { it.arenaId == arenaId }
        return if (own.size >= 2) own else SampleData.causePairs
    }
}
