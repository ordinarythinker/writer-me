package io.writerme.app.ui.state

import io.writerme.app.data.model.Note

class NoteState(
    val note: Note,
    val isHastTagBarVisible: Boolean = false,
    val isHistoryMode: Boolean = true,
)