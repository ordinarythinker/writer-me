package io.writerme.app.ui.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.realm.kotlin.ext.realmListOf
import io.writerme.app.R
import io.writerme.app.data.model.BookmarksFolder
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.component.Folder
import io.writerme.app.ui.component.Link
import io.writerme.app.ui.state.BookmarksState
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.light
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
    createFolder: (String, String, BookmarksFolder) -> Unit,
    onBackPressed: () -> Unit
) {
    val state = bookmarksState.collectAsStateWithLifecycle()
    val scaffoldState = rememberScaffoldState()
    val padding = dimensionResource(id = R.dimen.screen_padding)

    BackHandler(onBack = onBackPressed, enabled = true)

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
                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = stringResource(id = R.string.back_button),
                                tint = MaterialTheme.colors.light
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
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
                FloatingActionButton(onClick = showCreateFolderDialog) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = stringResource(id = R.string.add_folder_button)
                    )
                }
            },
            content = {
                Column {
                    if (state.value.isCreateDialogDisplayed) {
                        CreateFolderDialog(
                            createFolder = createFolder,
                            bookmarksFolder = state.value.currentFolder,
                            onDismiss = dismissCreateFolderDialog
                        )
                    }


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
                                            onClick = onLinkClicked
                                        )
                                    }
                                )
                            }
                        )
                    }
                }
            }
        )
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
            createFolder = { _, _, _ ->},
            onBackPressed = {}
        )
    }
}