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
import androidx.compose.material.Divider
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
import io.writerme.app.ui.theme.strokeLight
import io.writerme.app.utils.textFieldBackground

@Composable
fun CreateFolderDialogBody(
    createFolder: (name: String) -> Unit,
    onDismiss: () -> Unit,
) {
    val padding = dimensionResource(id = R.dimen.screen_padding)

    var name by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.padding(padding)
    ) {
        Text(
            modifier = Modifier.padding(0.dp, 8.dp, 0.dp, padding),
            text = stringResource(id = R.string.create_folder),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.light
        )

        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colors.strokeLight
        )

        Spacer(modifier = Modifier.height(padding))

        BasicTextField(
            value = name,
            maxLines = 1,
            onValueChange = { name = it },
            modifier = Modifier.textFieldBackground().fillMaxWidth(),
            singleLine = true,
            textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.light),
            cursorBrush = SolidColor(MaterialTheme.colors.light),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (name.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.folder_name),
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
            text = stringResource(id = R.string.create),
            modifier = Modifier.textFieldBackground()
                .wrapContentHeight()
                .padding(20.dp, 0.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    createFolder(name)
                    onDismiss()
                },
            color = MaterialTheme.colors.light
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CreateFolderDialogPreview() {
    WriterMeTheme {
        CreateFolderDialogBody(createFolder = {}, onDismiss = {})
    }
}