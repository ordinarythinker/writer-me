package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.History
import io.writerme.app.data.repository.NoteRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(): ViewModel() {

    private val noteRepository: NoteRepository = NoteRepository()
    private val changes : HashMap<Long, Int> = hashMapOf()

    init {
        addCloseable(noteRepository)
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

    fun saveChanges() {

    }

    override fun onCleared() {
        saveChanges()
        super.onCleared()
    }

    companion object {
        const val TAG = "NoteViewModel"
    }
}