package io.writerme.app.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.app.data.model.Component
import io.writerme.app.ui.state.ScrollableMediaState

@Composable
fun Task(task: Component) {

}

@Composable
@Preview(showBackground = true)
fun TaskPreview() {
    val component = Component()

    Task(component)
}
