package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.notifications.ObjectChange
import io.writerme.app.data.model.BookmarksFolder
import io.writerme.app.data.model.Component
import io.writerme.app.data.repository.BookmarksRepository
import io.writerme.app.ui.state.BookmarksState
import io.writerme.app.utils.scheduleImageLoading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val workManager: WorkManager
) : ViewModel() {

    private val bookmarksRepository: BookmarksRepository = BookmarksRepository()

    private val _bookmarksStateFlow: MutableStateFlow<BookmarksState> = MutableStateFlow(BookmarksState.empty())
    val bookmarksStateFlow: StateFlow<BookmarksState> = _bookmarksStateFlow

    private lateinit var bookmarksFlow: Flow<ObjectChange<BookmarksFolder>>

    init {
        addCloseable(bookmarksRepository)

        viewModelScope.launch {
            bookmarksFlow = bookmarksRepository.getMainFolder().asFlow()

            subscribeToCurrentFlow()
        }
    }

    private suspend fun subscribeToCurrentFlow() {
        bookmarksFlow.mapLatest {
            it.obj?.let { fdr ->
                _bookmarksStateFlow.emit(_bookmarksStateFlow.value.copy(currentFolder = fdr))
            }
        }.stateIn(viewModelScope)
    }

    fun onFolderClicked(folder: BookmarksFolder) {
        viewModelScope.launch {
            bookmarksRepository.getLatest(folder)?.let {
                bookmarksFlow = it.asFlow()

                subscribeToCurrentFlow()
            }
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

    fun toggleFloatingDialog() {
        viewModelScope.launch {
            val value = _bookmarksStateFlow.value
            _bookmarksStateFlow.emit(value.copy(
                isFloatingDialogShown = !value.isFloatingDialogShown
            ))
        }
    }

    fun navigateToParentFolder() {
        viewModelScope.launch {
            val value = _bookmarksStateFlow.value
            value.currentFolder.parent?.let { folder ->
                bookmarksRepository.getLatest(folder)?.let {
                    bookmarksFlow = it.asFlow()

                    subscribeToCurrentFlow()
                }
            }
        }
    }

    fun createFolder(name: String) {
        viewModelScope.launch {
            bookmarksRepository.createFolder(name, bookmarksStateFlow.value.currentFolder)
        }
    }

    fun createBookmark(url: String, title: String, parent: BookmarksFolder) {
        viewModelScope.launch {
            val bookmark = withContext(Dispatchers.Default) {
                return@withContext bookmarksRepository.createBookmark(url, title, parent)
            }

            workManager.scheduleImageLoading(bookmark.id, parent.id)
        }
    }

    fun deleteFolder(folder: BookmarksFolder) {
        viewModelScope.launch {
            bookmarksRepository.deleteFolder(folder)
        }
    }

    fun deleteBookmark(bookmark: Component) {
        viewModelScope.launch {
            bookmarksRepository.deleteBookmark(bookmark)
        }
    }

    fun toggleFolderDropdown(index: Int) {
        viewModelScope.launch {
            val state = _bookmarksStateFlow.value

            val newValue = if (index >= 0 && state.folderDropdownIndex != index) {
                index
            } else -1

            _bookmarksStateFlow.emit(state.copy(folderDropdownIndex = newValue))
        }
    }

    fun toggleBookmarkDropdown(index: Int) {
        viewModelScope.launch {
            val state = _bookmarksStateFlow.value

            val newValue = if (index >= 0 && state.bookmarkDropdownIndex != index) {
                index
            } else -1

            _bookmarksStateFlow.emit(state.copy(bookmarkDropdownIndex = newValue))
        }
    }
}