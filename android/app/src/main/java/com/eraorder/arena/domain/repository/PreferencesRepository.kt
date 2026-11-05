package com.eraorder.arena.domain.repository

import com.eraorder.arena.domain.model.AppPreferences

interface PreferencesRepository {
    fun load(): AppPreferences
    fun save(preferences: AppPreferences)
}
