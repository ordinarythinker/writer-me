package io.writerme.app.ui.state

import io.writerme.app.data.model.BookmarksFolder

class BookmarksState(
    val currentFolder: BookmarksFolder,
    val isBookmarkDialogDisplayed: Boolean = false,
    val isFolderDialogDisplayed: Boolean = false,
    val isFloatingDialogShown: Boolean = false
) {

    fun copy(
        folder: BookmarksFolder = this.currentFolder,
        isBookmarkDialogDisplayed: Boolean = this.isBookmarkDialogDisplayed,
        isFolderDialogDisplayed: Boolean = this.isFolderDialogDisplayed,
        isFloatingDialogShown: Boolean = this.isFloatingDialogShown
    ): BookmarksState {
        return BookmarksState(folder, isBookmarkDialogDisplayed, isFolderDialogDisplayed, isFloatingDialogShown)
    }
}