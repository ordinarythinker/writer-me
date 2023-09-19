package io.writerme.app.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.light
import io.writerme.app.utils.textFieldBackground

@Composable
fun AddLinkDialogBody(
    addLink: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Column {
        val padding = dimensionResource(id = R.dimen.screen_padding)
        var url by remember { mutableStateOf("") }

        BasicTextField(
            value = url,
            maxLines = 1,
            onValueChange = { url = it },
            modifier = Modifier.textFieldBackground().fillMaxWidth(),
            singleLine = true,
            textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.light),
            cursorBrush = SolidColor(MaterialTheme.colors.light),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (url.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.url),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.light
                        )
                    }
                    innerTextField()
                }
            },
        )

        Spacer(modifier = Modifier.height(padding))

        Text(
            text = stringResource(id = R.string.add),
            modifier = Modifier.textFieldBackground()
                .wrapContentHeight()
                .padding(20.dp, 0.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    addLink(url)
                    onDismiss()
                },
            color = MaterialTheme.colors.light
        )
    }
}

@Preview
@Composable
fun AddLinkDialogBodyPreview() {
    WriterMeTheme {
        AddLinkDialogBody({}, {})
    }
}