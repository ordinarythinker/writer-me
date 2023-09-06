package io.writerme.app.ui.state

import io.writerme.app.data.model.Note

data class NoteState(
    val note: Note,
    val isTagsBarVisible: Boolean = false,
    val isHistoryMode: Boolean = false
) {
    companion object {
        fun empty(): NoteState = NoteState(Note())
    }
}