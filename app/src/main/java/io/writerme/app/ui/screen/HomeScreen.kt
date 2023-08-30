package io.writerme.app.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
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
import androidx.navigation.NavController
import io.writerme.app.R
import io.writerme.app.ui.component.ProfileImage
import io.writerme.app.ui.state.HomeState
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.backgroundGrey
import io.writerme.app.ui.theme.light
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(stateFlow: StateFlow<HomeState>, navController: NavController? = null) {
    val state = stateFlow.collectAsStateWithLifecycle()

    val scaffoldState = rememberScaffoldState()
    val padding = dimensionResource(id = R.dimen.screen_padding)

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
            bottomBar = {
                BottomNavigation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                dimensionResource(id = R.dimen.big_radius)
                            )
                        ),
                    backgroundColor = MaterialTheme.colors.backgroundGrey,
                    elevation = dimensionResource(id = R.dimen.shadow),

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BottomNavigationItem(
                            selected = true,
                            onClick = { },
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
                            onClick = { },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_tasks),
                                    contentDescription = stringResource(id = R.string.tasks_screen),
                                    tint = MaterialTheme.colors.light
                                )
                            }
                        )

                        BottomNavigationItem(
                            selected = false,
                            onClick = { },
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
                            onClick = { },
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
                            .fillMaxWidth()
                            .padding(padding),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfileImage(url = state.value.profilePhotoUrl)

                        Column(
                            modifier = Modifier
                                .weight(0.6f)
                                .padding(padding, 0.dp)
                        ) {
                            Text(
                                text = state.value.firstName, // TODO: here should be the time-depending greeting function
                                style = MaterialTheme.typography.h1,
                                color = MaterialTheme.colors.light
                            )

                            Text(
                                text = state.value.firstName,
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.light,
                                modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val main = HomeState()

    val flow = MutableStateFlow(main)

    WriterMeTheme {
        HomeScreen(stateFlow = flow)
    }
}