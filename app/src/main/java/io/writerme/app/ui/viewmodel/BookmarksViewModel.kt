package io.writerme.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.writerme.app.data.repository.BookmarksRepository

class BookmarksViewModel: ViewModel() {

    private val bookmarksRepository: BookmarksRepository = BookmarksRepository(viewModelScope)

    init {
        addCloseable(bookmarksRepository)
    }
}