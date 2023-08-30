package io.writerme.app.ui.state

import io.writerme.app.data.model.Note

data class HomeState(
    val firstName: String = "",
    val profilePhotoUrl: String = "",
    val isImportantVisible: String = "",
    val isFoldersVisible: String = "",
    var notes: List<Note> = listOf()
)