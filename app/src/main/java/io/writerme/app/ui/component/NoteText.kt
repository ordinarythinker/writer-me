package io.writerme.app.ui.component

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.app.R
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.light

@Composable
fun NoteText(
    component: Component,
    onValueChange: (Component) -> Unit,
) {
    var localText by remember {
        mutableStateOf(component.content)
    }

    if (component.type == ComponentType.Text) {
        BasicTextField(
            value = localText.ifEmpty { stringResource(id = R.string.type_your_note_here) },
            onValueChange = {
                localText = it
                component.content = it
                onValueChange(component)
            },
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