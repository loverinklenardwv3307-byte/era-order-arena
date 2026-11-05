package com.eraorder.arena.domain.repository

import com.eraorder.arena.domain.model.LearnerProgress

interface ProgressRepository {
    fun load(): LearnerProgress
    fun save(progress: LearnerProgress)
}
