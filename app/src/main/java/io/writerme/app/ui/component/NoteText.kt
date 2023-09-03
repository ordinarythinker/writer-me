package io.writerme.app.ui.component

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.light

@Composable
fun NoteText(
    component: Component,
    onValueChange: (String) -> Unit,
) {
    if (component.type == ComponentType.Text) {
        BasicTextField(
            value = component.content,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.light)
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun NoteTextPreview() {
    val component = Component().apply {
        type = ComponentType.Text
        content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry..."
    }

    WriterMeTheme {
        NoteText(
            component = component,
            onValueChange = {}
        )
    }
}