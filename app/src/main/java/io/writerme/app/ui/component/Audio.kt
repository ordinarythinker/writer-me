package io.writerme.app.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.app.ui.state.AudioState

@Composable
fun Audio(audioState: AudioState) {
}

@Composable
@Preview(showBackground = true)
fun AudioPreview() {
    val audioState = AudioState()

    Audio(audioState = audioState)
}