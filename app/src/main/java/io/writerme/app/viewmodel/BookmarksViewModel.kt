package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.writerme.app.data.model.BookmarksFolder
import io.writerme.app.data.repository.BookmarksRepository

class BookmarksViewModel: ViewModel() {

    private val bookmarksRepository: BookmarksRepository = BookmarksRepository(viewModelScope)

    fun showCreateFolderDialog() {

    }

    fun dismissCreateFolderDialog() {

    }

    fun createBookmark(url: String, title: String, parent: BookmarksFolder) {

    }

    init {
        addCloseable(bookmarksRepository)
    }
}