package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.realm.kotlin.notifications.ObjectChange
import io.writerme.app.data.model.Settings
import io.writerme.app.data.repository.SettingsRepository
import io.writerme.app.ui.state.SettingsState
import io.writerme.app.utils.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val settingsRepository = SettingsRepository(viewModelScope)

    private val _settingsState: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState.empty())
    val settingsState: StateFlow<SettingsState> = _settingsState

    private lateinit var _settingsSource: Flow<ObjectChange<Settings>>

    init {
        addCloseable(settingsRepository)

        viewModelScope.launch {
            _settingsSource = settingsRepository.getSettings()
        }
    }


    private fun toState(settings: Settings) {
        viewModelScope.launch(Dispatchers.Main) {
            val state = settings.toState()

            state.languages = Const.SUPPORTED_LANGUAGES

            _settingsState.emit(state)
        }
    }

    fun onLanguageChange(language: String) {

    }

    fun onDarkModeChange(isDarkMode: Boolean) {

    }

    fun onTermsClick() {
        // Open the website in the browser
    }

    fun onCounterChange(key: String, value: Int) {

    }
}