package io.writerme.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.model.History
import io.writerme.app.data.model.Note
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.linkTitle

@Composable
fun Note(note: Note) {
    val padding = dimensionResource(id = R.dimen.screen_padding)

    val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

    Card(
        shape = shape,
        modifier = Modifier
            .wrapContentHeight()
            .shadow(dimensionResource(id = R.dimen.shadow), shape),
        backgroundColor = Color.Blue
    ) {
        if (note.cover != null && note.cover!!.newest() != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.Transparent)
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.travel),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                /*AsyncImage(
                    model = note.cover!!.newest()!!.imageUrl,
                    contentDescription = note.cover!!.newest()!!.content,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )*/

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(12.dp, 0.dp, 12.dp, 12.dp)
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.big_radius)))
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(MaterialTheme.colors.linkTitle)
                            .blur(60.dp)
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(padding, 4.dp)
                    ) {
                        note.title?.newest()?.let { component ->
                            Text(
                                text = component.title,
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        if (note.content.isNotEmpty() && note.content[0].isNotEmpty()
                            && note.content[0].newest()!!.type == ComponentType.Text) {

                            Text(
                                text = note.content[0].newest()!!.content,
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        } else {
            // TODO
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NotePreview() {
    val titleComponent = Component().apply {
        title = "Day #12: Adventures started"
        type = ComponentType.Text
    }

    val text = Component().apply {
        content = "It's all started with a simple thought that I wasn't able to control anymore. It gradually became an addiction: to travel and to see the world"
        type = ComponentType.Text
    }

    val note = Note().apply {
        this.title = History(titleComponent)
        this.cover = History(Component())
        this.content.add(History(text))
    }

    WriterMeTheme {
        Note(note)
    }
}