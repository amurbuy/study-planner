package com.example.studyplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyplanner.data.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    val darkTheme: StateFlow<Boolean> =
        SettingsRepository.darkTheme.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun setDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            SettingsRepository.setDarkTheme(enabled)
        }
    }
}