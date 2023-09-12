package io.writerme.app.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.writerme.app.R
import io.writerme.app.ui.component.ProfileImage
import io.writerme.app.ui.component.TabSwitcher
import io.writerme.app.ui.state.HomeState
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.backgroundGrey
import io.writerme.app.ui.theme.light
import io.writerme.app.utils.toGreeting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    stateFlow: StateFlow<HomeState>,
    toggleSearchMode: () -> Unit,
    openTasksScreen: () -> Unit,
    openBookmarksScreen: () -> Unit,
    openSettingsScreen: () -> Unit,

) {
    val state = stateFlow.collectAsStateWithLifecycle()

    val scaffoldState = rememberScaffoldState()
    val padding = dimensionResource(id = R.dimen.screen_padding)

    val fabShape = RoundedCornerShape(50.dp)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_main),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(id = R.string.background_image),
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    modifier = Modifier.padding(top = 8.dp),
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.h2,
                            color = MaterialTheme.colors.light
                        )
                    },
                    navigationIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_writer_me),
                            contentDescription = stringResource(id = R.string.back_button),
                            tint = MaterialTheme.colors.light,
                            modifier = Modifier
                                .padding(start = padding)
                                .height(40.dp)
                        )
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    shape = fabShape
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = stringResource(id = R.string.create_note)
                    )
                }
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center,
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.clip(
                            RoundedCornerShape(
                                dimensionResource(id = R.dimen.big_radius)
                            )
                        ),
                    backgroundColor = MaterialTheme.colors.backgroundGrey,
                    cutoutShape = fabShape,

                ) {
                    BottomNavigation (
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp
                    ) {
                        BottomNavigationItem(
                            selected = true,
                            onClick = toggleSearchMode,
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_home),
                                    contentDescription = stringResource(id = R.string.home_screen),
                                    tint = MaterialTheme.colors.light
                                )
                            }
                        )

                        BottomNavigationItem(
                            selected = false,
                            onClick = openTasksScreen,
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_tasks),
                                    contentDescription = stringResource(id = R.string.tasks_screen),
                                    tint = MaterialTheme.colors.light
                                )
                            }
                        )

                        Spacer(modifier = Modifier.width(80.dp))

                        BottomNavigationItem(
                            selected = false,
                            onClick = openBookmarksScreen,
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_bookmark),
                                    contentDescription = stringResource(id = R.string.bookmarks_screen),
                                    tint = MaterialTheme.colors.light
                                )
                            }
                        )

                        BottomNavigationItem(
                            selected = false,
                            onClick = openSettingsScreen,
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_settings),
                                    contentDescription = stringResource(id = R.string.settings_task),
                                    tint = MaterialTheme.colors.light
                                )
                            }
                        )
                    }
                }
            }
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = padding, top = padding, end = padding, bottom = 70.dp)
            ) {
                item {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfileImage(url = state.value.profilePhotoUrl)

                        Column(
                            modifier = Modifier
                                .weight(0.6f)
                                .padding(padding, 0.dp)
                        ) {
                            Text(
                                text = Date().toGreeting(),
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.light
                            )

                            Text(
                                text = state.value.firstName,
                                style = MaterialTheme.typography.h1,
                                color = MaterialTheme.colors.light,
                                modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(padding))

                    TabSwitcher(
                        tabs = state.value.tabs,
                        chosen = state.value.chosenTab,
                        onItemChosen = {}
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val main = HomeState(
        firstName = "Florian",
        tabs = listOf("All", "Important"),
        chosenTab = "All"
    )

    val flow = MutableStateFlow(main)

    WriterMeTheme {
        HomeScreen(stateFlow = flow, {}, {}, {}, {})
    }
}