package com.nexora.circle.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = ThemeRepository(app)

    val settings: StateFlow<ThemeSettings> = repository.settings
        .stateIn(viewModelScope, SharingStarted.Eagerly, ThemeSettings())

    fun save(settings: ThemeSettings) {
        viewModelScope.launch { repository.save(settings) }
    }
}
