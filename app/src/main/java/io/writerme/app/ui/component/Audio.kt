package io.writerme.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.writerme.app.R
import io.writerme.app.data.audio.MediaListener
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.state.AudioState
import io.writerme.app.ui.theme.backgroundGrey
import io.writerme.app.ui.theme.light
import io.writerme.app.utils.toTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun Audio(
    audioState: StateFlow<AudioState>,
    audioListener: MediaListener,
    modifier: Modifier = Modifier
) {
    val state = audioState.collectAsStateWithLifecycle()

    if (state.value.audio.type == ComponentType.Voice) {
        val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

        Card(
            shape = shape,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Transparent)
                .shadow(dimensionResource(id = R.dimen.shadow), shape),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Box(modifier = Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colors.backgroundGrey)
                )

                Row(
                    modifier = modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    IconButton(onClick = { /*TODO*/ }) {
                        val isPaused = audioListener.isPaused()
                        Icon(
                            painter = if (isPaused) {
                                painterResource(id = R.drawable.ic_play)
                            } else painterResource(id = R.drawable.ic_pause),

                            contentDescription = if (isPaused) {
                                stringResource(id = R.string.play_button)
                            } else stringResource(id = R.string.pause_button),

                            tint = MaterialTheme.colors.light
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row {
                            // TODO: here should be the progress bar
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // TODO: here will be progress indicators
                            Text(
                                text = state.value.currentProgress.toTime(),
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.light
                            )
                            
                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = state.value.entireLength.toTime(),
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.light
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AudioPreview() {
    val component = Component().apply {
        type = ComponentType.Voice
    }
    val audioState = AudioState(
        audio = component,
        currentProgress = 30,
        entireLength = 100
    )
    val listener = object : MediaListener {
        override fun scrollTo(time: Long) {}
        override fun pause() {}
        override fun resume() {}
        override fun isPaused(): Boolean {
            return true
        }
    }

    Audio(audioState = MutableStateFlow(audioState), listener)
}