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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import java.util.Date

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Task(task: Component, onClick: () -> Unit, modifier: Modifier = Modifier) {
    if (task.type == ComponentType.Task) {
        val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

        Card(
            shape = shape,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Transparent)
                .shadow(dimensionResource(id = R.dimen.shadow), shape),
            onClick = onClick
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
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
                        contentDescription = null,
                        tint = MaterialTheme.colors.light
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TaskPreview() {
    val component = Component().apply {
        content = "Meeting with Anna"
        time = Date()
        type = ComponentType.Task
    }

    WriterMeTheme {
        Task(component, {})
    }
}
