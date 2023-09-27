package io.writerme.app.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.notifications.ObjectChange
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.model.History
import io.writerme.app.data.model.Note
import io.writerme.app.data.repository.NoteRepository
import io.writerme.app.ui.navigation.NoteScreen
import io.writerme.app.ui.state.NoteState
import io.writerme.app.utils.scheduleImageLoading
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
    private val savedState: SavedStateHandle,
    private val workManager: WorkManager
): ViewModel() {

    private val noteRepository: NoteRepository = NoteRepository()
    private val changes : HashMap<Long, Int> = hashMapOf()

    private val pendingUpdates = mutableMapOf<Long, Component>()
    private val saveFlow = MutableSharedFlow<Component>()

    private lateinit var _noteSource: Flow<ObjectChange<Note>>

    private val _noteState: MutableStateFlow<NoteState> = MutableStateFlow(NoteState.empty())
    val noteState: StateFlow<NoteState> = _noteState

    // TODO: NOTHING IS UPDATED ON USER SCREEN!!!!!!!!!!

    init {
        addCloseable(noteRepository)

        viewModelScope.launch {
            val id: String? = savedState[NoteScreen.NOTE_PARAM]

            Log.d(TAG, "id saved state: $id")

            val noteId = id?.toLongOrNull()

            _noteSource = if (noteId != null) {
                noteRepository.getNote(noteId)
            } else noteRepository.createNewNote()

            _noteSource.mapLatest {
                Log.d(TAG, "change is mapped")
                it.obj?.let { updatedNote ->
                    Log.d(TAG, "note's updated")
                    _noteState.emit(_noteState.value.copy(note = updatedNote))
                }
            }.stateIn(viewModelScope)
        }

        saveFlow.debounce(300)
            .onEach { component ->
                Log.d(TAG, "text is mapped")
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
        viewModelScope.launch {
            pendingUpdates[component.id] = component
            saveFlow.emit(component)
        }
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

    fun addImageSection(url: String) {
        viewModelScope.launch {
            val component = Component().apply {
                this.noteId = _noteState.value.note.id
                this.imageUrl = url
                this.type = ComponentType.Image
            }

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

    fun updateCoverImage(uri: String) {
        viewModelScope.launch {
            val noteId = _noteState.value.note.id

            noteRepository.updateNoteCoverImage(noteId, uri)
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
        viewModelScope.launch {
            val state = _noteState.value

            _noteState.emit(state.copy(expandedDropdownId = -1))
        }
    }

    fun toggleDropDownHistoryMode()  {
        viewModelScope.launch {
            val state = _noteState.value

            _noteState.emit(state.copy(isDropDownInHistoryMode = !state.isDropDownInHistoryMode))
        }
    }

    fun addLinkSection(url : String) {
        viewModelScope.launch {
            val noteId = _noteState.value.note.id

            val component = Component().apply {
                this.noteId = noteId
                this.type = ComponentType.Link
                this.url = url
            }

            val link = noteRepository.saveComponent(component)
            workManager.scheduleImageLoading(link.id)

            noteRepository.addSection(noteId, link)
        }
    }

    fun toggleAddLinkDialogVisibility() {
        viewModelScope.launch {
            val state = _noteState.value

            _noteState.emit(state.copy(isAddLinkDialogDisplayed = !state.isAddLinkDialogDisplayed))
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