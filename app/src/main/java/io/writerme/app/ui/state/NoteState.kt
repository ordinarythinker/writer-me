package io.writerme.app.ui.state

import io.writerme.app.data.model.Note

data class NoteState(
    val note: Note,
    val isHastTagBarVisible: Boolean = false,
    val isHistoryMode: Boolean = false,
    val tags: List<String>
) {
    companion object {
        fun empty(): NoteState = NoteState(Note(), tags = listOf())
    }
}