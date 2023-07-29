package io.writerme.app.ui.screen

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.data.model.BookmarksFolder
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.dialogBackground
import io.writerme.app.ui.theme.fieldDark
import io.writerme.app.ui.theme.light
import io.writerme.app.ui.theme.strokeLight

@Composable
fun CreateFolderDialog(
    addFolder: (BookmarksFolder) -> Unit,
    openFolderPicker: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))
    val padding = dimensionResource(id = R.dimen.screen_padding)

    var url by remember {
        mutableStateOf("Link")
    }

    var title by remember {
        mutableStateOf("Title")
    }

    var path by remember {
        mutableStateOf("Path")
    }

    Card(
        shape = shape,
        modifier = modifier
            .width(80.dp)
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
                    text = stringResource(id = R.string.create_bookmark_title),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.light,
                    modifier = Modifier.padding(0.dp, 8.dp, 0.dp, padding)
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
                    value = url,
                    maxLines = 1,
                    onValueChange = {},
                    modifier = backgroundModifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.light),
                    decorationBox = { innerTextField ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(padding))

                BasicTextField(
                    value = title,
                    maxLines = 1,
                    onValueChange = {},
                    modifier = backgroundModifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.light),
                    decorationBox = { innerTextField ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(padding))

                Row (
                    modifier = backgroundModifier.fillMaxWidth().clickable {
                        openFolderPicker()
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = path,
                        modifier = Modifier.weight(0.8f),
                        color = MaterialTheme.colors.light,
                        style = MaterialTheme.typography.body1
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_folder),
                        contentDescription = stringResource(id = R.string.folder_icon),
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colors.light
                    )
                }

                Spacer(modifier = Modifier.height(padding))

                Text(
                    text = stringResource(id = R.string.create),
                    modifier = backgroundModifier
                        .wrapContentHeight()
                        .padding(20.dp, 0.dp)
                        .align(Alignment.CenterHorizontally).clickable {
                                                                       
                        },
                    color = MaterialTheme.colors.light
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateFolderDialogPreview() {
    WriterMeTheme {
        CreateFolderDialog(addFolder = {}, openFolderPicker = {})
    }
}