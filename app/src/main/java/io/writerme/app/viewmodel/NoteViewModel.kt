package io.writerme.app.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.notifications.ObjectChange
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.History
import io.writerme.app.data.model.Note
import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.ui.navigation.NoteScreen
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
class NoteViewModel @Inject constructor(
    private val savedState: SavedStateHandle
): ViewModel() {

    private val noteRepository: NoteRepository = NoteRepository()
    private val changes : HashMap<Long, Int> = hashMapOf()

    private val pendingUpdates = mutableMapOf<Long, Component>()
    private val saveFlow = MutableSharedFlow<Component>()

    private lateinit var _noteSource: Flow<ObjectChange<Note>>

    private val _noteState: MutableStateFlow<NoteState> = MutableStateFlow(NoteState.empty())
    val noteState: StateFlow<NoteState> = _noteState

    init {
        addCloseable(noteRepository)

        viewModelScope.launch {
            val id: String? = savedState[NoteScreen.NOTE_PARAM]

            _noteSource = if (id == null) {
                noteRepository.createNewNote()
            } else {
                val noteId = id.toLongOrNull()

                if (noteId != null) {
                    noteRepository.getNote(noteId)
                } else noteRepository.createNewNote()
            }

            _noteSource.mapLatest {
                it.obj?.let { note ->
                    _noteState.emit(_noteState.value.copy(note = note))
                }
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

    fun addNewCheckBox(position: Int) {
        viewModelScope.launch {
            noteRepository.addNewCheckBox(_noteState.value.note.id, position)
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

    fun addSection(component: Component) {
        viewModelScope.launch {
            noteRepository.addSection(_noteState.value.note.id, component)
        }
    }

    fun toggleHistoryMode() {
        viewModelScope.launch {
            val state = _noteState.value
            _noteState.emit(state.copy(isHistoryMode = !state.isHistoryMode))
        }
    }

    fun toggleTopBarDropdownVisibility() {
        viewModelScope.launch {
            val state = _noteState.value
            _noteState.emit(state.copy(isTopBarDropdownVisible = !state.isTopBarDropdownVisible))
        }
    }

    fun addCoverImage(noteId: Long) {
        // TODO: this function is very questionable
    }

    fun onTitleChange(text: String) {
        viewModelScope.launch {
            val titleComponent = Component(_noteState.value.note, text)

            noteRepository.updateNoteTitle(titleComponent.noteId, titleComponent)
        }
    }

    fun showHashtagBar(show : Boolean) {
        viewModelScope.launch {
            val state = _noteState.value
            _noteState.emit(state.copy(isTagsBarVisible = show))
        }
    }

    fun addNewTag(tag: String) {
        viewModelScope.launch {
            noteRepository.addNewTag(_noteState.value.note.id, tag)
        }
    }

    fun deleteTag(tag: String) {
        viewModelScope.launch {
            noteRepository.deleteTag(_noteState.value.note.id, tag)
        }
    }

    fun showDropdown(index: Int) {
        val state = _noteState.value
        viewModelScope.launch {
            _noteState.emit(state.copy(expandedDropdownId = index))
        }
    }

    fun dismissDropDown() {
        val state = _noteState.value
        viewModelScope.launch {
            _noteState.emit(state.copy(expandedDropdownId = -1))
        }
    }

    fun toggleDropDownHistoryMode()  {
        val state = _noteState.value
        viewModelScope.launch {
            _noteState.emit(state.copy(isDropDownInHistoryMode = !state.isDropDownInHistoryMode))
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