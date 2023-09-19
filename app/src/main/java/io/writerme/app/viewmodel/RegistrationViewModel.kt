package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.writerme.app.data.repository.SettingsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor() : ViewModel() {
    private val settingsRepository = SettingsRepository()

    init {
        addCloseable(settingsRepository)
    }

    fun saveName(name: String) {
        viewModelScope.launch {
            settingsRepository.saveName(name)
        }
    }
}