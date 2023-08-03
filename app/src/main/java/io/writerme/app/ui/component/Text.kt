package io.writerme.app.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.light

@Composable
fun NoteText(
    component: Component,
    isHistoryMode: Boolean,
    onValueChange: (String) -> Unit,
    onMoreClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (component.type == ComponentType.Text) {
        Column(
            modifier = modifier
        ) {
            AnimatedVisibility(
                visible = isHistoryMode,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = stringResource(id = R.string.more),
                    tint = MaterialTheme.colors.light,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 8.dp)
                        .size(24.dp)
                        .clickable { onMoreClicked() }
                )
            }

            BasicTextField(
                value = component.content,
                onValueChange = onValueChange,
                textStyle = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.light)
            )
        }
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
            isHistoryMode = true,
            onValueChange = {},
            onMoreClicked = {},
            modifier = Modifier.padding(16.dp).background(Color.DarkGray)
        )
    }
}