package io.writerme.app.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.app.data.model.Note

@Composable
fun Note(note: Note) {

}

@Composable
@Preview(showBackground = true)
fun NotePreview() {
    val note = Note()
    Note(note)
}