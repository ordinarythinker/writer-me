package io.writerme.app.ui.state

import io.writerme.app.data.model.History
import io.writerme.app.data.model.Note

data class NoteState(
    val note: Note,
    val isTagsBarVisible: Boolean = false,
    val isTopBarDropdownVisible: Boolean = false,
    val isDropDownInHistoryMode: Boolean = false,
    val isAddLinkDialogDisplayed: Boolean = false,
    val expandedDropdownId: Int = -1,
    val isHistoryMode: Boolean = false
) {
    companion object {
        fun empty(): NoteState {
            val note = Note().apply {
                this.title = History()
                this.cover = History()
            }

            return NoteState(note)
        }
    }
}