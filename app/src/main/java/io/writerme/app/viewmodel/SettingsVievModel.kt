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

    private val settingsRepository = SettingsRepository()

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

    fun onCounterChange(key: String, increase: Boolean) {
        viewModelScope.launch {
            var value = 1

            when(key) {
                Const.TEXT_CHANGES_HISTORY_KEY -> {
                    val current = _settingsState.value.textChanges

                    if (increase) {
                        if (current < Const.TEXT_CHANGES_HISTORY) {
                            value = current + 1
                        }
                    } else {
                        if (current > 1) {
                            value = current - 1
                        }
                    }
                }
                Const.MEDIA_CHANGES_HISTORY_KEY -> {
                    val current = _settingsState.value.mediaChanges

                    if (increase) {
                        if (current < Const.MEDIA_CHANGES_HISTORY) {
                            value = current + 1
                        }
                    } else {
                        if (current > 1) {
                            value = current - 1
                        }
                    }
                }
                Const.VOICE_CHANGES_HISTORY_KEY -> {
                    val current = _settingsState.value.voiceChanges

                    if (increase) {
                        if (current < Const.VOICE_CHANGES_HISTORY) {
                            value = current + 1
                        }
                    } else {
                        if (current > 1) {
                            value = current - 1
                        }
                    }
                }
                Const.TASK_CHANGES_HISTORY_KEY -> {
                    val current = _settingsState.value.taskChanges

                    if (increase) {
                        if (current < Const.TASK_CHANGES_HISTORY) {
                            value = current + 1
                        }
                    } else {
                        if (current > 1) {
                            value = current - 1
                        }
                    }
                }
                Const.LINK_CHANGES_HISTORY_KEY -> {
                    val current = _settingsState.value.linkChanges

                    if (increase) {
                        if (current < Const.LINK_CHANGES_HISTORY) {
                            value = current + 1
                        }
                    } else {
                        if (current > 1) {
                            value = current - 1
                        }
                    }
                }
            }

            settingsRepository.setCounter(key, value)
        }
    }
}