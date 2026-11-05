package com.eraorder.arena.domain.repository

import com.eraorder.arena.domain.model.Arena
import com.eraorder.arena.domain.model.CausePair
import com.eraorder.arena.domain.model.HistoryEvent

interface EventRepository {
    fun arenas(): List<Arena>
    fun arena(arenaId: String): Arena
    fun events(arenaId: String): List<HistoryEvent>
    fun allEvents(): List<HistoryEvent>
    fun causePairs(arenaId: String): List<CausePair>
}
