package io.writerme.app.ui.state

import io.writerme.app.data.model.BookmarksFolder

class BookmarksState(
    val currentFolder: BookmarksFolder,
    val isBookmarkDialogDisplayed: Boolean = false,
    val isFolderDialogDisplayed: Boolean = false,
    val isCreatingFloatingMenuShown: Boolean = false
) {

    fun copy(
        folder: BookmarksFolder = this.currentFolder,
        isBookmarkDialogDisplayed: Boolean = this.isBookmarkDialogDisplayed,
        isFolderDialogDisplayed: Boolean = this.isFolderDialogDisplayed,
        isCreatingFloatingMenuShown: Boolean = this.isCreatingFloatingMenuShown
    ): BookmarksState {
        return BookmarksState(folder, isBookmarkDialogDisplayed, isFolderDialogDisplayed, isCreatingFloatingMenuShown)
    }
}