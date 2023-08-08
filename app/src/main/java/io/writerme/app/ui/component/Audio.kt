package io.writerme.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.writerme.app.R
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.state.AudioState
import io.writerme.app.ui.theme.backgroundGrey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun Audio(
    audioState: StateFlow<AudioState>,
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
                    Column(
                        modifier = Modifier.weight(0.8f)
                    ) {

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
    val audioState = AudioState(component)

    Audio(audioState = MutableStateFlow(audioState))
}