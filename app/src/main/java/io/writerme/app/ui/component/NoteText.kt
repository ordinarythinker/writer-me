package io.writerme.app.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
    placeholderResource : Int = R.string.type_your_note_here,
    typography: TextStyle = MaterialTheme.typography.subtitle1,
    modifier: Modifier = Modifier
) {
    var localText by remember {
        mutableStateOf("")
    }

    if (localText.isEmpty()) {
        localText = component.content
    }

    if (component.type == ComponentType.Text) {
        BasicTextField(
            value = localText,
            onValueChange = {
                localText = it
                onValueChange(component.copy(content = it))
            },
            modifier = modifier,
            textStyle = typography.copy(color = MaterialTheme.colors.light),
            cursorBrush = SolidColor(MaterialTheme.colors.light),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (localText.isEmpty()) {
                        Text(
                            text = stringResource(id = placeholderResource),
                            style = typography,
                            color = MaterialTheme.colors.light
                        )
                    }
                    innerTextField()
                }
            },
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