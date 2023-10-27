package io.writerme.app.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.realm.kotlin.ext.realmListOf
import io.writerme.app.R
import io.writerme.app.data.model.BookmarksFolder
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.component.CreateBookmarkDialog
import io.writerme.app.ui.component.CreateFolderDialogBody
import io.writerme.app.ui.component.Folder
import io.writerme.app.ui.component.Link
import io.writerme.app.ui.component.TitledDialog
import io.writerme.app.ui.state.BookmarksState
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.dialogBackground
import io.writerme.app.ui.theme.fieldDark
import io.writerme.app.ui.theme.light
import io.writerme.app.ui.theme.strokeLight
import io.writerme.app.utils.checkAndRequestPermission
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookmarksScreen(
    bookmarksState: StateFlow<BookmarksState>,
    onFolderClicked: (BookmarksFolder) -> Unit,
    onLinkClicked: (Component) -> Unit,
    showCreateFolderDialog: () -> Unit,
    dismissCreateFolderDialog: () -> Unit,
    showCreateBookmarkDialog: () -> Unit,
    dismissCreateBookmarkDialog: () -> Unit,
    showFloatingDialog: () -> Unit,
    dismissFloatingDialog: () -> Unit,
    navigateToParentFolder: () -> Unit,
    createBookmark: (String, String, BookmarksFolder) -> Unit,
    createFolder: (String) -> Unit,
    deleteFolder: (BookmarksFolder) -> Unit,
    deleteBookmark: (Component) -> Unit,
    dismissScreen: () -> Unit
) {
    val state = bookmarksState.collectAsStateWithLifecycle()
    val scaffoldState = rememberScaffoldState()
    val padding = dimensionResource(id = R.dimen.screen_padding)

    checkAndRequestPermission(
        permission = Manifest.permission.INTERNET,
        onSuccess = {},
        onNotGrantedMessage = R.string.we_wont_load_images
    )

    BackHandler(
        onBack = {
            if (state.value.isBookmarkDialogDisplayed) {
                dismissCreateBookmarkDialog()
            } else if (state.value.isFolderDialogDisplayed) {
                dismissCreateFolderDialog()
            } else if (state.value.isFloatingDialogShown) {
                dismissFloatingDialog()
            } else if (state.value.currentFolder.hasParentFolder()) {
                navigateToParentFolder()
            }
        },
        enabled = state.value.currentFolder.hasParentFolder() || state.value.isBookmarkDialogDisplayed
                    || state.value.isFloatingDialogShown || state.value.isFolderDialogDisplayed
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_main),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    title = {
                        Text(
                            text = state.value.currentFolder.name.ifEmpty {
                                stringResource(id = R.string.bookmarks)
                            },
                            style = MaterialTheme.typography.h2,
                            color = MaterialTheme.colors.light
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (state.value.currentFolder.hasParentFolder()) {
                                navigateToParentFolder()
                            } else {
                                dismissScreen()
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = stringResource(id = R.string.back_button),
                                tint = MaterialTheme.colors.light
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            // implement in the future
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_more),
                                contentDescription = stringResource(id = R.string.more),
                                tint = MaterialTheme.colors.light
                            )
                        }
                    }
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .matchParentSize()
                            .padding(end = 70.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        AnimatedVisibility(
                            visible = state.value.isFloatingDialogShown,
                            enter = slideInHorizontally(initialOffsetX = { it/2 }) + fadeIn(),
                            exit = slideOutHorizontally(targetOffsetX = { it/2 }) + fadeOut()
                        ) {
                            val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

                            Card(
                                shape = shape,
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .shadow(dimensionResource(id = R.dimen.shadow), shape),
                                backgroundColor = Color.Transparent
                            ) {
                                Row (
                                    modifier = Modifier
                                        .clip(shape)
                                        .background(MaterialTheme.colors.dialogBackground)
                                        .padding(8.dp)
                                        .shadow(dimensionResource(id = R.dimen.shadow), shape),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.bookmark),
                                        modifier = Modifier
                                            .clip(shape)
                                            .background(MaterialTheme.colors.fieldDark)
                                            .border(
                                                dimensionResource(id = R.dimen.field_border_width),
                                                MaterialTheme.colors.strokeLight,
                                                shape
                                            )
                                            .clickable {
                                                showCreateBookmarkDialog()
                                                dismissFloatingDialog()
                                            }
                                            .padding(padding, 12.dp)
                                            .height(40.dp),
                                        color = MaterialTheme.colors.light,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = stringResource(id = R.string.folder),
                                        modifier = Modifier
                                            .clip(shape)
                                            .background(MaterialTheme.colors.fieldDark)
                                            .border(
                                                dimensionResource(id = R.dimen.field_border_width),
                                                MaterialTheme.colors.strokeLight,
                                                shape
                                            )
                                            .clickable {
                                                showCreateFolderDialog()
                                                dismissFloatingDialog()
                                            }
                                            .padding(padding, 12.dp)
                                            .height(40.dp),
                                        color = MaterialTheme.colors.light,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    FloatingActionButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = {
                            showFloatingDialog()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = stringResource(id = R.string.add_folder_bookmark)
                        )
                    }
                }
            },
            content = {
                Column {
                    if (state.value.isBookmarkDialogDisplayed) {
                        CreateBookmarkDialog(
                            createBookmark = createBookmark,
                            bookmarksFolder = state.value.currentFolder,
                            onDismiss = dismissCreateBookmarkDialog
                        )
                    }

                    AnimatedVisibility(
                        visible = state.value.currentFolder.folders.isNotEmpty()
                    ) {
                        Row {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(120.dp),
                                contentPadding = PaddingValues(padding),
                                content = {

                                    items(
                                        items = state.value.currentFolder.folders,
                                        itemContent = { item ->
                                            Surface(
                                                onClick = { onFolderClicked(item) },
                                                color = Color.Transparent
                                            ) {
                                                Folder(
                                                    folder = item,
                                                    modifier = Modifier
                                                        .width(110.dp)
                                                        .padding(8.dp)
                                                )
                                            }
                                        }
                                    )

                                }
                            )
                        }
                    }

                    Row {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(150.dp),
                            contentPadding = PaddingValues(padding),
                            content = {
                                val links = state.value.currentFolder.bookmarks

                                items(
                                    items = links,
                                    itemContent = {item ->
                                        Link(
                                            link = item,
                                            modifier = Modifier.padding(8.dp),
                                            onClick = onLinkClicked,
                                            height = 130.dp
                                        )
                                    }
                                )
                            }
                        )
                    }
                }
            }
        )

        if (state.value.isFolderDialogDisplayed) {
            TitledDialog(
                title = stringResource(id = R.string.create_folder),
                onDismiss = dismissCreateFolderDialog,
                content = {
                    CreateFolderDialogBody(
                        createFolder = {
                            createFolder(it)
                            dismissCreateFolderDialog()
                        },
                        onDismiss = dismissCreateFolderDialog
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookmarksScreenPreview() {
    val mainFolder = BookmarksFolder()
    val job = BookmarksFolder().apply {
        name = "Job"
        parent = mainFolder
    }
    mainFolder.apply {
        this.folders = realmListOf(
            job,
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

    val state = BookmarksState(mainFolder, false)

    WriterMeTheme {
        BookmarksScreen(
            bookmarksState = MutableStateFlow(state),
            onFolderClicked = {},
            onLinkClicked = {},
            showCreateFolderDialog = {},
            dismissCreateFolderDialog = {},
            showCreateBookmarkDialog = {},
            dismissCreateBookmarkDialog = {},
            showFloatingDialog = {},
            dismissFloatingDialog = {},
            navigateToParentFolder = {},
            createBookmark = { _, _, _ ->},
            createFolder = {},
            deleteFolder = {},
            deleteBookmark = {},
            dismissScreen = {}
        )
    }
}