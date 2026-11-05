package com.eraorder.arena.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eraorder.arena.domain.model.AppPreferences
import com.eraorder.arena.domain.model.DateFormat
import com.eraorder.arena.domain.model.TextSize
import com.eraorder.arena.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArenaPreferencesViewModel(
    private val preferences: PreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ArenaPreferencesUiState())
    val state: StateFlow<ArenaPreferencesUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val stored = preferences.load()
            _state.update {
                it.copy(
                    dateFormat = stored.dateFormat,
                    textSize = stored.textSize,
                    haptics = stored.haptics,
                    sound = stored.sound,
                    loaded = true
                )
            }
        }
    }

    fun selectDateFormat(index: Int) {
        val value = if (index == 0) DateFormat.BCE_CE else DateFormat.BC_AD
        _state.update { it.copy(dateFormat = value) }
        persist()
    }

    fun selectTextSize(index: Int) {
        val value = if (index == 0) TextSize.STANDARD else TextSize.LARGE
        _state.update { it.copy(textSize = value) }
        persist()
    }

    fun setHaptics(enabled: Boolean) {
        _state.update { it.copy(haptics = enabled) }
        persist()
    }

    fun setSound(enabled: Boolean) {
        _state.update { it.copy(sound = enabled) }
        persist()
    }

    private fun persist() {
        val current = _state.value
        viewModelScope.launch {
            preferences.save(
                AppPreferences(
                    dateFormat = current.dateFormat,
                    textSize = current.textSize,
                    haptics = current.haptics,
                    sound = current.sound
                )
            )
        }
    }
}
