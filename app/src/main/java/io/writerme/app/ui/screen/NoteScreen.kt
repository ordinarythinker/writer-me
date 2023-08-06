package io.writerme.app.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.writerme.app.R
import io.writerme.app.ui.state.NoteState
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.backgroundGrey
import io.writerme.app.ui.theme.light
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    noteState: StateFlow<NoteState>,
    showHashtagBar: (Boolean) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    val focusRequester = remember { FocusRequester() }

    val state = noteState.collectAsStateWithLifecycle()

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
                    title = {
                        Text(
                            text = stringResource(id = R.string.edit),
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
                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_more),
                                contentDescription = stringResource(id = R.string.more),
                                tint = MaterialTheme.colors.light
                            )
                        }
                    }
                )
            },
            content = {

            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                dimensionResource(id = R.dimen.big_radius)
                            )
                        ),
                    backgroundColor = MaterialTheme.colors.backgroundGrey,
                    elevation = dimensionResource(id = R.dimen.shadow)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        /*
                        // TODO: pending feature

                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_bold),
                                contentDescription = stringResource(id = R.string.bold_icon)
                            )
                        }
                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_italic),
                                contentDescription = stringResource(id = R.string.italic_icon)
                            )
                        }
                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_underline),
                                contentDescription = stringResource(id = R.string.underline_icon)
                            )
                        }
                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_spacing),
                                contentDescription = stringResource(id = R.string.spacing_icon)
                            )
                        }

                        */

                        IconButton(onClick = {
                            if (!state.value.isHastTagBarVisible) {
                                showHashtagBar(true)
                            }
                            focusRequester.requestFocus()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_hashtag),
                                contentDescription = stringResource(id = R.string.add_hashtag_button),
                                tint = MaterialTheme.colors.light
                            )
                        }

                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_link),
                                contentDescription = stringResource(id = R.string.add_link_button),
                                tint = MaterialTheme.colors.light
                            )
                        }

                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_microphone),
                                contentDescription = stringResource(id = R.string.add_voice_note_button),
                                tint = MaterialTheme.colors.light
                            )
                        }

                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_checked),
                                contentDescription = stringResource(id = R.string.add_tast_button),
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colors.light
                            )
                        }

                        IconButton(onClick = {  }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = stringResource(id = R.string.add_media_button),
                                tint = MaterialTheme.colors.light
                            )
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun NoteScreenPreview() {
    val noteState = NoteState()
    val state = MutableStateFlow(noteState)

    WriterMeTheme {
        NoteScreen(noteState = state, {})
    }
}