package com.eraorder.arena.data.local

import android.content.Context
import android.content.SharedPreferences

class PreferencesStore(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE)

    fun int(key: String, fallback: Int): Int = prefs.getInt(key, fallback)

    fun putInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun bool(key: String, fallback: Boolean): Boolean = prefs.getBoolean(key, fallback)

    fun putBool(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun text(key: String, fallback: String): String = prefs.getString(key, fallback) ?: fallback

    fun putText(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    companion object {
        private const val STORE_NAME = "era_order_arena"
        const val KEY_BEST_STREAK = "best_streak"
        const val KEY_ROUNDS = "rounds"
        const val KEY_TOTAL = "total_placements"
        const val KEY_CORRECT = "correct_placements"
        const val KEY_LEARNED = "learned_ids"
        const val KEY_CENTURIES = "century_hits"
        const val KEY_REGIONS = "region_hits"
        const val KEY_DATE_FORMAT = "date_format"
        const val KEY_TEXT_SIZE = "text_size"
        const val KEY_HAPTICS = "haptics"
        const val KEY_SOUND = "sound"
    }
}
