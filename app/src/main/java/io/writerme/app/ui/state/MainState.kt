package io.writerme.app.ui.state

import io.writerme.app.data.model.Note

class MainState {
    var userName: String = ""
    var notes: List<Note> = listOf()
}