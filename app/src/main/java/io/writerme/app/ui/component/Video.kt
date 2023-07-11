package io.writerme.app.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.app.ui.state.ScrollableMediaState

@Composable
fun Video(videoState: ScrollableMediaState) {

}

@Composable
@Preview(showBackground = true)
fun VideoPreview() {
    val videoState = ScrollableMediaState()

    Video(videoState)
}
