package io.writerme.app.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.app.data.model.BookmarksFolder
import io.writerme.app.ui.state.BookmarksState
import io.writerme.app.ui.theme.WriterMeTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BookmarksScreen(state: StateFlow<BookmarksState>) {

}

@Preview(showBackground = true)
@Composable
fun BookmarksScreenPreview() {
    val mainFolder = BookmarksFolder()

    val state = BookmarksState(mainFolder)

    WriterMeTheme {
        BookmarksScreen(state = MutableStateFlow(state))
    }
}