package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.notifications.ResultsChange
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.History
import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.ui.state.NoteState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class NoteViewModel @Inject constructor(): ViewModel() {

    private val noteRepository: NoteRepository = NoteRepository()
    private val changes : HashMap<Long, Int> = hashMapOf()

    private val pendingUpdates = mutableMapOf<Long, Component>()
    private val saveFlow = MutableSharedFlow<Component>()

    private lateinit var _componentsSource: Flow<ResultsChange<Component>>

    private val _noteState: MutableStateFlow<NoteState> = MutableStateFlow(NoteState.empty())
    val noteState: StateFlow<NoteState> = _noteState

    init {
        addCloseable(noteRepository)

        viewModelScope.launch {
            _componentsSource.mapLatest {
                //_noteState.emit(_noteState.value.copy(components = it.list))
            }.stateIn(viewModelScope)
        }

        saveFlow.debounce(300)
            .onEach { component ->
                pendingUpdates.remove(component.id)
                noteRepository.saveComponent(component)
            }.launchIn(viewModelScope)
    }

    fun modifyHistory(history: History, component: Component) {
        val change = changes.getOrDefault(component.id, 0)

        viewModelScope.launch {
            if (change > 0) {
                noteRepository.saveComponent(component)
            } else {
                noteRepository.updateHistory(history.id, component.copy())
                changes[component.id] = 1
            }
        }
    }

    fun onComponentChange(component: Component) {
        pendingUpdates[component.id] = component
        saveFlow.tryEmit(component)
    }

    fun addNewCheckBox(noteId: Long, position: Int) {
        viewModelScope.launch {
            noteRepository.addNewCheckBox(noteId, position)
        }
    }

    fun saveChanges() {
        pendingUpdates.forEach { (k, v) ->
            viewModelScope.launch {
                noteRepository.saveComponent(v)
                pendingUpdates.remove(k)
            }
        }
    }

    fun addSection(noteId: Long, component: Component) {
        viewModelScope.launch {
            noteRepository.addSection(noteId, component)
        }
    }

    override fun onCleared() {
        saveChanges()
        super.onCleared()
    }

    companion object {
        const val TAG = "NoteViewModel"
    }
}