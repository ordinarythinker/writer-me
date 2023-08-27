package io.writerme.app.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.writerme.app.ui.state.MainState
import io.writerme.app.ui.theme.WriterMeTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(stateFlow: StateFlow<MainState>) {
    val state = stateFlow.collectAsStateWithLifecycle()

}

@Preview
@Composable
fun HomeScreenPreview() {
    val main = MainState()

    val flow = MutableStateFlow(main)

    WriterMeTheme {
        HomeScreen(stateFlow = flow)
    }
}