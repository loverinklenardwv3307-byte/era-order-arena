package com.eraorder.arena.data.repository

import com.eraorder.arena.data.local.PreferencesStore
import com.eraorder.arena.domain.model.AppPreferences
import com.eraorder.arena.domain.model.DateFormat
import com.eraorder.arena.domain.model.TextSize
import com.eraorder.arena.domain.repository.PreferencesRepository

class PreferencesRepositoryImpl(private val store: PreferencesStore) : PreferencesRepository {

    override fun load(): AppPreferences = AppPreferences(
        dateFormat = DateFormat.entries.firstOrNull {
            it.name == store.text(PreferencesStore.KEY_DATE_FORMAT, DateFormat.BCE_CE.name)
        } ?: DateFormat.BCE_CE,
        textSize = TextSize.entries.firstOrNull {
            it.name == store.text(PreferencesStore.KEY_TEXT_SIZE, TextSize.STANDARD.name)
        } ?: TextSize.STANDARD,
        haptics = store.bool(PreferencesStore.KEY_HAPTICS, true),
        sound = store.bool(PreferencesStore.KEY_SOUND, true)
    )

    override fun save(preferences: AppPreferences) {
        store.putText(PreferencesStore.KEY_DATE_FORMAT, preferences.dateFormat.name)
        store.putText(PreferencesStore.KEY_TEXT_SIZE, preferences.textSize.name)
        store.putBool(PreferencesStore.KEY_HAPTICS, preferences.haptics)
        store.putBool(PreferencesStore.KEY_SOUND, preferences.sound)
    }
}
