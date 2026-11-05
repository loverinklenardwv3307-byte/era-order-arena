package com.eraorder.arena.presentation.app

import androidx.lifecycle.ViewModel
import com.eraorder.arena.core.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {

    private val _screen = MutableStateFlow<Screen>(Screen.Splash)
    val screen: StateFlow<Screen> = _screen.asStateFlow()

    private val _canGoBack = MutableStateFlow(false)
    val canGoBack: StateFlow<Boolean> = _canGoBack.asStateFlow()

    private val backStack = ArrayDeque<Screen>()
    private var sessionCounter = 0L

    fun navigate(target: Screen) {
        val current = _screen.value
        if (current != Screen.Splash) backStack.addLast(current)
        _screen.value = target
        _canGoBack.value = backStack.isNotEmpty()
    }

    fun replace(target: Screen) {
        _screen.value = target
        _canGoBack.value = backStack.isNotEmpty()
    }

    fun openMenu() {
        backStack.clear()
        _screen.value = Screen.Menu
        _canGoBack.value = false
    }

    fun startGame(arenaId: String, daily: Boolean) {
        sessionCounter += 1
        navigate(Screen.Game(arenaId, daily, sessionCounter))
    }

    fun restartGame(arenaId: String, daily: Boolean) {
        sessionCounter += 1
        while (backStack.isNotEmpty() && backStack.last() is Screen.Game) backStack.removeLast()
        replaceWithGame(arenaId, daily)
    }

    fun finishSession(target: Screen.GameOver) {
        while (backStack.isNotEmpty() && backStack.last() is Screen.Game) backStack.removeLast()
        _screen.value = target
        _canGoBack.value = backStack.isNotEmpty()
    }

    fun back() {
        val previous = backStack.removeLastOrNull() ?: return
        _screen.value = previous
        _canGoBack.value = backStack.isNotEmpty()
    }

    private fun replaceWithGame(arenaId: String, daily: Boolean) {
        _screen.value = Screen.Game(arenaId, daily, sessionCounter)
        _canGoBack.value = backStack.isNotEmpty()
    }
}
