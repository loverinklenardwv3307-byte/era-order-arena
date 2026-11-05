package com.eraorder.arena.domain.model

data class Arena(
    val id: String,
    val name: String,
    val eraRange: String,
    val spriteKey: String,
    val primer: String,
    val eventCount: Int
)
