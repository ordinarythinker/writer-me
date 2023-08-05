package io.writerme.app.ui.state

import io.writerme.app.data.model.BookmarksFolder

class BookmarksState(
    val currentFolder: BookmarksFolder,
    val isCreateDialogDisplayed: Boolean = false
)