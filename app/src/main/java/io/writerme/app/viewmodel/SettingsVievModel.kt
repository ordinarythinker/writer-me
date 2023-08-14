package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.realm.kotlin.notifications.ObjectChange
import io.writerme.app.data.model.Settings
import io.writerme.app.data.repository.SettingsRepository
import io.writerme.app.ui.state.SettingsState
import io.writerme.app.utils.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModel : ViewModel() {

    private val settingsRepository = SettingsRepository(viewModelScope)

    private lateinit var _settingsSource: Flow<ObjectChange<Settings>>

    private val _settingsState: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState.empty())
    val settingsState: StateFlow<SettingsState> = _settingsState


    init {
        addCloseable(settingsRepository)

        viewModelScope.launch {
            _settingsSource = settingsRepository.getSettings()

            _settingsSource.mapLatest {
                it.obj?.let {  settings ->
                    toState(settings)
                }
            }.stateIn(viewModelScope)
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
        if (language in Const.SUPPORTED_LANGUAGES) {
            viewModelScope.launch {
                settingsRepository.setLanguage(language)
            }
        }
    }

    fun onDarkModeChange(isDarkMode: Boolean) {
        viewModelScope.launch {
            settingsRepository.setDarkMode(isDarkMode)
        }
    }

    fun onCounterChange(key: String, value: Int) {
        viewModelScope.launch {
            settingsRepository.setCounter(key, value)
        }
    }
}