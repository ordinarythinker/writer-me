package io.writerme.app.ui.state

import io.writerme.app.data.model.BookmarksFolder

class BookmarksState(
    val currentFolder: BookmarksFolder,
    val isBookmarkDialogDisplayed: Boolean = false,
    val isFolderDialogDisplayed: Boolean = false
)