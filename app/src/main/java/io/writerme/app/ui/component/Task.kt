package io.writerme.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.backgroundGrey
import io.writerme.app.ui.theme.light
import io.writerme.app.utils.toDateDescription

@Composable
fun Task(task: Component, modifier: Modifier = Modifier) {
    if (task.type == ComponentType.Task) {
        val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

        Row(
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .clip(shape)
                .background(MaterialTheme.colors.backgroundGrey)
                .padding(8.dp)
                .shadow(dimensionResource(id = R.dimen.shadow), shape),
        ) {
            Column(
                modifier = Modifier.weight(0.8f)
            ) {
                Text(
                    text = toDateDescription(task.time),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.light
                )
                Text(
                    text = task.content,
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.light
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_proceed),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TaskPreview() {
    val component = Component().apply {
        content = "Meeting with Anna"
        type = ComponentType.Task
    }

    WriterMeTheme {
        Task(component, Modifier.padding(16.dp))
    }
}
