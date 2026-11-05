package com.eraorder.arena.core.navigation

sealed interface Screen {
    data object Splash : Screen
    data object Menu : Screen
    data object ArenaSelect : Screen
    data class Game(val arenaId: String, val daily: Boolean, val sessionId: Long) : Screen
    data class GameOver(
        val arenaId: String,
        val daily: Boolean,
        val placed: Int,
        val streak: Int,
        val lives: Int,
        val newRecord: Boolean
    ) : Screen

    data object PersonalTimeline : Screen
    data object LearnerStats : Screen
    data object ArenaPreferences : Screen
}
