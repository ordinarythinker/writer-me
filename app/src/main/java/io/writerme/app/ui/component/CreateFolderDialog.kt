package io.writerme.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.dialogBackground
import io.writerme.app.ui.theme.fieldDark
import io.writerme.app.ui.theme.light
import io.writerme.app.ui.theme.strokeLight

@Composable
fun CreateFolderDialog(
    createFolder: (name: String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))
    val padding = dimensionResource(id = R.dimen.screen_padding)

    var name by remember {
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            shape = shape,
            modifier = modifier
                .wrapContentHeight()
                .shadow(dimensionResource(id = R.dimen.shadow), shape)
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(MaterialTheme.colors.dialogBackground)
                        .blur(dimensionResource(id = R.dimen.blur_radius))
                )

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

                    val fieldShape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

                    val backgroundModifier = Modifier
                        .clip(fieldShape)
                        .border(
                            dimensionResource(id = R.dimen.field_border_width),
                            MaterialTheme.colors.strokeLight,
                            fieldShape
                        )
                        .background(MaterialTheme.colors.fieldDark)
                        .padding(padding, 0.dp)
                        .height(40.dp)

                    BasicTextField(
                        value = name,
                        maxLines = 1,
                        onValueChange = { name = it },
                        modifier = backgroundModifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.light),
                        decorationBox = { innerTextField ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
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
                        modifier = backgroundModifier
                            .wrapContentHeight()
                            .padding(20.dp, 0.dp)
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                createFolder(name)
                            },
                        color = MaterialTheme.colors.light
                    )
                }

            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CreateFolderDialogPreview() {
    WriterMeTheme {
        CreateFolderDialog(createFolder = {}, onDismiss = {}, modifier = Modifier)
    }
}