package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.notifications.ObjectChange
import io.realm.kotlin.notifications.ResultsChange
import io.writerme.app.data.model.Note
import io.writerme.app.data.model.Settings
import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.data.repository.SettingsRepository
import io.writerme.app.ui.component.HomeFilterTab
import io.writerme.app.ui.state.HomeState
import io.writerme.app.utils.toFirstName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val settingsRepository: SettingsRepository = SettingsRepository()
    private val notesRepository: NoteRepository = NoteRepository()

    private val _homeStateFlow: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val homeStateFlow: StateFlow<HomeState> = _homeStateFlow

    private lateinit var settingsFlow: Flow<ObjectChange<Settings>>
    private lateinit var notesFlow: Flow<ResultsChange<Note>>

    private val _displayedTab = MutableStateFlow(HomeFilterTab.All)

    init {
        addCloseable(settingsRepository)
        addCloseable(notesRepository)

        viewModelScope.launch {

            settingsFlow = settingsRepository.getSettings().asFlow()

            settingsFlow.mapLatest {
                val current = _homeStateFlow.value
                it.obj?.let { settings ->
                    _homeStateFlow.emit(current.copy(
                        firstName = settings.fullName.toFirstName(),
                        profilePhotoUrl = settings.profilePictureUrl
                    ))
                }
            }.stateIn(viewModelScope)

            notesFlow = notesRepository.getNotes()

            notesFlow.mapLatest {
                val current = _homeStateFlow.value
                val list = it.list.toList()

                val isImportantVisible = list.any { note -> note.isImportant }

                _homeStateFlow.emit(current.copy(notes = list, isImportantVisible = isImportantVisible))
            }.stateIn(viewModelScope)

            homeStateFlow.mapLatest {
                if (it.chosenTab != _displayedTab.value) {
                    _displayedTab.emit(it.chosenTab)
                }
            }.stateIn(viewModelScope)

            _displayedTab.mapLatest { tab ->
                notesFlow.collectLatest {
                    val current = _homeStateFlow.value

                    val results = when (tab) {
                        HomeFilterTab.All -> it.list

                        HomeFilterTab.Important -> {
                            it.list.filter { note ->
                                note.isImportant
                            }.sortedByDescending { note ->
                                note.changeTime
                            }
                        }
                    }

                    _homeStateFlow.emit(current.copy(notes = results.toList()))
                }
            }.stateIn(viewModelScope)
        }
    }

    fun toggleImportance(noteId: Long) {
        viewModelScope.launch {
            notesRepository.toggleImportance(noteId)
        }
    }

    fun toggleSearchMode() {
        viewModelScope.launch {
            val current = _homeStateFlow.value
            _homeStateFlow.emit(
                current.copy(isSearchMode = !current.isSearchMode)
            )
        }
    }

    fun onTabChosen(tab: HomeFilterTab) {
        viewModelScope.launch {
            _homeStateFlow.emit(
                _homeStateFlow.value.copy(chosenTab = tab)
            )
        }
    }

    fun toggleNoteDropdown(index: Int) {
        viewModelScope.launch {
            val current = _homeStateFlow.value

            val newValue = if (current.expandedDropdownId != index) {
                index
            } else -1 // hide dropdown

            _homeStateFlow.emit(
                _homeStateFlow.value.copy(expandedDropdownId = newValue)
            )
        }
    }
}