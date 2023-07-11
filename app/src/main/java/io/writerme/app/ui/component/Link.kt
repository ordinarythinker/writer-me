package io.writerme.app.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.app.data.model.Component

@Composable
fun Link(task: Component) {

}

@Composable
@Preview(showBackground = true)
fun LinkPreview() {
    val component = Component()

    Link(component)
}