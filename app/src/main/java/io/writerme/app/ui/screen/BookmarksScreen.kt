package io.writerme.app.ui.screen

import android.annotation.SuppressLint
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
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookmarksScreen(
    bookmarksState: StateFlow<BookmarksState>,
    onLinkClicked: (Component) -> Unit
) {
    val state = bookmarksState.collectAsStateWithLifecycle()
    val scaffoldState = rememberScaffoldState()
    val padding = dimensionResource(id = R.dimen.screen_padding)

    Box {
        Image(
            painter = painterResource(id = R.drawable.background_main),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    title = {
                        Text(
                            text = stringResource(id = R.string.bookmarks),
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
                FloatingActionButton(onClick = {

                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = stringResource(id = R.string.add_folder_button)
                    )
                }
            },
            content = {
                Column {
                    Row {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(120.dp),
                            contentPadding = PaddingValues(padding),
                            content = {
                                val list = state.value.mainFolder.folders
                                items(list.size) { index ->
                                    Folder(
                                        folder = list[index],
                                        modifier = Modifier
                                            .width(110.dp)
                                            .padding(8.dp)
                                    )
                                }
                            }
                        )
                    }

                    Row {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(150.dp),
                            contentPadding = PaddingValues(padding),
                            content = {
                                val links = state.value.mainFolder.bookmarks

                                items(links.size) { i ->
                                    Link(
                                        link = links[i],
                                        modifier = Modifier.padding(8.dp),
                                        onClick = onLinkClicked
                                    )
                                }
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
    mainFolder.apply {
        this.folders = realmListOf(
            BookmarksFolder().apply { name = "Job" },
            BookmarksFolder().apply { name = "Programming" },
            BookmarksFolder().apply { name = "Films" }
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

    val state = BookmarksState(mainFolder)

    WriterMeTheme {
        BookmarksScreen(bookmarksState = MutableStateFlow(state)) {}
    }
}