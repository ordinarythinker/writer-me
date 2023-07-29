package io.writerme.app.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.state.ScrollableMediaState

@Composable
fun Video(videoState: ScrollableMediaState) {
    // Implementation pending
}

@Composable
@Preview(showBackground = true)
fun MediaPreview() {
    val component = Component().apply {
        type = ComponentType.Video
    }
    val videoState = ScrollableMediaState(component)

    Video(videoState)
}
