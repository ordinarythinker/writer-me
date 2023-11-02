package io.writerme.app.ui.state

import io.writerme.app.data.model.BookmarksFolder

data class BookmarksState(
    val currentFolder: BookmarksFolder,
    val isBookmarkDialogDisplayed: Boolean = false,
    val isFolderDialogDisplayed: Boolean = false,
    val isFloatingDialogShown: Boolean = false,
    val folderDropdownIndex: Int = -3,
    val bookmarkDropdownIndex: Int = -3
) {
    companion object {
        fun empty() : BookmarksState {
            return BookmarksState(BookmarksFolder())
        }
    }
}