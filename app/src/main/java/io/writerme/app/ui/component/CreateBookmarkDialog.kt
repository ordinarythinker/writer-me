package io.writerme.app.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.realm.kotlin.ext.realmListOf
import io.writerme.app.R
import io.writerme.app.data.model.BookmarksFolder
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.dialogBackground
import io.writerme.app.ui.theme.fieldDark
import io.writerme.app.ui.theme.light
import io.writerme.app.ui.theme.strokeLight
import io.writerme.app.utils.textFieldBackground

@Composable
fun CreateBookmarkDialog(
    createBookmark: (url: String, title: String, parent: BookmarksFolder) -> Unit,
    bookmarksFolder: BookmarksFolder,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))
    val padding = dimensionResource(id = R.dimen.screen_padding)

    var url by remember {
        mutableStateOf("")
    }

    var title by remember {
        mutableStateOf("")
    }

    var path by remember {
        mutableStateOf("Path")
    }

    var isFolderChoosingMode by remember {
        mutableStateOf(false)
    }

    var folder by remember {
        mutableStateOf(bookmarksFolder)
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
                    Row(
                        modifier = Modifier.padding(0.dp, 8.dp, 0.dp, padding),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedVisibility(visible = isFolderChoosingMode) {
                            Icon(
                                painterResource(id = R.drawable.ic_back),
                                contentDescription = stringResource(id = R.string.back_button),
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(0.dp, 0.dp, 16.dp, 0.dp)
                                    .clickable { isFolderChoosingMode = false },
                                tint = MaterialTheme.colors.light
                            )
                        }

                        Text(
                            text = if (isFolderChoosingMode) {
                                stringResource(id = R.string.choose_folder)
                            } else {
                                stringResource(id = R.string.create_bookmark_title)
                            },
                            style = MaterialTheme.typography.h4,
                            color = MaterialTheme.colors.light
                        )
                    }

                    Divider(
                        thickness = 1.dp,
                        color = MaterialTheme.colors.strokeLight
                    )

                    Spacer(modifier = Modifier.height(padding))

                    val fieldShape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

                    AnimatedVisibility(visible = !isFolderChoosingMode) {
                        Column {
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
                                                text = stringResource(id = R.string.link),
                                                style = MaterialTheme.typography.body1,
                                                color = MaterialTheme.colors.light
                                            )
                                        }
                                        innerTextField()
                                    }
                                },
                            )

                            Spacer(modifier = Modifier.height(padding))

                            BasicTextField(
                                value = title,
                                maxLines = 1,
                                onValueChange = { title = it },
                                singleLine = true,
                                modifier = Modifier.textFieldBackground().fillMaxWidth(),
                                textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.light),
                                cursorBrush = SolidColor(MaterialTheme.colors.light),
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        if (url.isEmpty()) {
                                            Text(
                                                text = stringResource(id = R.string.title),
                                                style = MaterialTheme.typography.body1,
                                                color = MaterialTheme.colors.light
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                            )

                            Spacer(modifier = Modifier.height(padding))

                            Row (
                                modifier = Modifier.textFieldBackground()
                                    .fillMaxWidth()
                                    .clickable {
                                        if (bookmarksFolder.folders.isNotEmpty()) {
                                            isFolderChoosingMode = true
                                        }
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = path,
                                    modifier = Modifier.weight(0.8f),
                                    color = MaterialTheme.colors.light,
                                    style = MaterialTheme.typography.body1
                                )
                                if (bookmarksFolder.folders.isNotEmpty()) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_folder),
                                        contentDescription = stringResource(id = R.string.folder_icon),
                                        modifier = Modifier.size(20.dp),
                                        tint = MaterialTheme.colors.light
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(padding))

                            Text(
                                text = stringResource(id = R.string.create),
                                modifier = Modifier.textFieldBackground()
                                    .wrapContentHeight()
                                    .padding(20.dp, 0.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable {
                                        createBookmark(url, title, folder)
                                        onDismiss()
                                    },
                                color = MaterialTheme.colors.light
                            )
                        }
                    }

                    AnimatedVisibility(visible = isFolderChoosingMode) {
                        Column {
                            LazyColumn(
                                modifier = Modifier
                                    .clip(fieldShape)
                                    .border(
                                        dimensionResource(id = R.dimen.field_border_width),
                                        MaterialTheme.colors.strokeLight,
                                        fieldShape
                                    )
                                    .background(MaterialTheme.colors.fieldDark)
                                    .heightIn(0.dp, 300.dp)
                                    .padding(16.dp, 8.dp)
                                    .fillMaxWidth()
                            ) {
                                if (folder.hasParentFolder()) {
                                    item {
                                        FolderPickerRow(
                                            folder = BookmarksFolder().apply {
                                                name = ".."
                                            },
                                            modifier = Modifier.clickable {
                                                folder = folder.parent!!
                                            }
                                        )
                                    }
                                }

                                items(
                                    items = bookmarksFolder.folders,
                                    itemContent = { item ->
                                        FolderPickerRow(
                                            folder = item,
                                            modifier = Modifier.clickable {
                                                folder = item
                                            }
                                        )
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.height(padding))

                            Text(
                                text = stringResource(id = R.string.choose),
                                modifier = Modifier.textFieldBackground()
                                    .wrapContentHeight()
                                    .padding(20.dp, 0.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable {
                                        isFolderChoosingMode = false
                                        path = folder.path
                                    },
                                color = MaterialTheme.colors.light
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateBookmarkDialogPreview() {
    val mainFolder = BookmarksFolder()

    mainFolder.apply {
        this.folders = realmListOf(
            BookmarksFolder().apply {
                name = "Job"
                parent = mainFolder
            },
            BookmarksFolder().apply {
                name = "Programming"
                parent = mainFolder
            },
            BookmarksFolder().apply {
                name = "Films"
                parent = mainFolder
            }
        )

        this.bookmarks = realmListOf(
            Component().apply {
                type = ComponentType.Link
                title = "Top Travel Guide"
            },
            Component().apply {
                type = ComponentType.Link
                title = "Houses for Rent: Your best option"
            }
        )
    }

    WriterMeTheme {
        CreateBookmarkDialog(createBookmark = { _, _, _ -> }, onDismiss = {}, bookmarksFolder = mainFolder)
    }
}