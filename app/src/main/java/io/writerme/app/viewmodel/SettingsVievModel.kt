package io.writerme.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.writerme.app.R
import io.writerme.app.WriterMe
import io.writerme.app.data.model.Settings
import io.writerme.app.data.repository.SettingsRepository
import io.writerme.app.ui.state.SettingsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsRepository = SettingsRepository(viewModelScope)

    private val _settingsState: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState.empty())
    val settingsState: StateFlow<SettingsState> = _settingsState

    private val _settingsSource =
        settingsRepository
            .getSettings()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s ->
                s?.let { toState(it) }
            }

    private fun toState(settings: Settings) {
        viewModelScope.launch(Dispatchers.Main) {
            val state = settings.toState()

            val resources = this@SettingsViewModel.getApplication<WriterMe>().resources

            val languages = listOf(
                resources.getString(R.string.lang_1),
                resources.getString(R.string.lang_2),
                resources.getString(R.string.lang_3),
                resources.getString(R.string.lang_4),
                resources.getString(R.string.lang_5),
                resources.getString(R.string.lang_6)
            )

            state.languages = languages

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

    override fun onCleared() {
        _settingsSource.dispose()
    }
}