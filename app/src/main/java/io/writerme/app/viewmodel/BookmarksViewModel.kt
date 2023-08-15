package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.notifications.ObjectChange
import io.writerme.app.data.model.BookmarksFolder
import io.writerme.app.data.repository.BookmarksRepository
import io.writerme.app.ui.state.BookmarksState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class BookmarksViewModel: ViewModel() {

    private val bookmarksRepository: BookmarksRepository = BookmarksRepository(viewModelScope)

    private lateinit var _bookmarksStateFlow: MutableStateFlow<BookmarksState>
    lateinit var bookmarksStateFlow: StateFlow<BookmarksState>

    private lateinit var bookmarksFlow: Flow<ObjectChange<BookmarksFolder>>

    init {
        addCloseable(bookmarksRepository)

        viewModelScope.launch {
            val folder = bookmarksRepository.getMainFolder()

            val state = BookmarksState(folder)
            _bookmarksStateFlow = MutableStateFlow(state)
            bookmarksStateFlow = _bookmarksStateFlow

            bookmarksFlow = folder.asFlow()

            bookmarksFlow.mapLatest {
                it.obj?.let { fdr ->
                    _bookmarksStateFlow.emit(_bookmarksStateFlow.value.copy(folder = fdr))
                }
            }.stateIn(viewModelScope)
        }
    }

    fun showCreateFolderDialog() {
        viewModelScope.launch {
            _bookmarksStateFlow.emit(_bookmarksStateFlow.value.copy(
                isFolderDialogDisplayed = true
            ))
        }
    }

    fun dismissCreateFolderDialog() {
        viewModelScope.launch {
            _bookmarksStateFlow.emit(_bookmarksStateFlow.value.copy(
                isFolderDialogDisplayed = false
            ))
        }
    }

    fun showCreateBookmarkDialog() {
        viewModelScope.launch {
            _bookmarksStateFlow.emit(_bookmarksStateFlow.value.copy(
                isBookmarkDialogDisplayed = true
            ))
        }
    }

    fun dismissCreateBookmarkDialog() {
        viewModelScope.launch {
            _bookmarksStateFlow.emit(_bookmarksStateFlow.value.copy(
                isBookmarkDialogDisplayed = false
            ))
        }
    }

    fun createFolder(name: String, parent: BookmarksFolder? = null) {
        viewModelScope.launch {
            bookmarksRepository.createFolder(name, parent)
        }
    }

    fun createBookmark(url: String, title: String, parent: BookmarksFolder) {
        viewModelScope.launch {
            bookmarksRepository.createBookmark(url, title, parent)
        }
    }

    init {
        addCloseable(bookmarksRepository)
    }
}