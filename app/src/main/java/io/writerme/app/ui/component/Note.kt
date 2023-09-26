package io.writerme.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.realm.kotlin.ext.realmListOf
import io.writerme.app.R
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.model.History
import io.writerme.app.data.model.Note
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.lightGrey
import io.writerme.app.utils.toDateDescription
import java.util.Calendar

@Composable
fun Note(
    note: Note,
    modifier: Modifier = Modifier
) {
    val padding = dimensionResource(id = R.dimen.screen_padding)
    val verticalSpacing = padding / 2

    val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

    Card(
        shape = shape,
        modifier = modifier
            .wrapContentHeight()
            .heightIn(120.dp, 400.dp)
            .shadow(dimensionResource(id = R.dimen.shadow), shape),
        backgroundColor = Color.White
    ) {
        if (note.cover != null && note.cover!!.newest() != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.Transparent)
            ) {
                AsyncImage(
                    model = note.cover!!.newest()!!.imageUrl,
                    contentDescription = note.cover!!.newest()!!.content,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                if (
                    note.title != null
                        && note.title!!.newest() != null
                        && note.title!!.newest()!!.title.isNotEmpty()
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(12.dp, 0.dp, 12.dp, 12.dp)
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.big_radius)))
                    ) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(MaterialTheme.colors.lightGrey)
                                .blur(60.dp)
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(padding, 4.dp)
                        ) {
                            note.title?.newest()?.let { component ->
                                Text(
                                    text = component.content,
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
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        note.title?.newest()?.let { component ->
                            Text(
                                text = component.content,
                                style = MaterialTheme.typography.h5,
                                modifier = Modifier.fillMaxWidth(0.85f),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        if (note.isImportant) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_heart),
                                contentDescription = stringResource(id = R.string.note_important_icon),
                                modifier = Modifier
                                    .shadow(elevation = 4.dp, HeartShape())
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(verticalSpacing))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(note.tags) {tag ->
                            Text(
                                text = "#$tag",
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(padding))
                                    .background(MaterialTheme.colors.lightGrey)
                                    .padding(padding, 4.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }

                items(items = note.content) { history ->
                    history.newest()?.let { component ->
                        Spacer(modifier = Modifier.height(verticalSpacing))

                        when (component.type) {
                            ComponentType.Text -> {
                                Text(
                                    text = component.content,
                                    style = MaterialTheme.typography.body2
                                )
                            }

                            ComponentType.Checkbox -> {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Spacer(Modifier.width(4.dp))

                                    Icon(
                                        modifier = Modifier
                                            .width(10.dp)
                                            .height(10.dp)
                                            .padding(top = 3.dp),
                                        painter = if (component.isChecked) painterResource(id = R.drawable.ic_checked)
                                        else painterResource(id = R.drawable.ic_unchecked),
                                        contentDescription = stringResource(id = R.string.checkbox_name)
                                    )
                                    Spacer(Modifier.width(6.dp))

                                    Text(
                                        text = component.content,
                                        style = MaterialTheme.typography.body2,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }

                            ComponentType.Voice -> {
                                // pending
                            }
                            ComponentType.Task -> {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = component.time.toDateDescription(),
                                            style = MaterialTheme.typography.h5,
                                            modifier = Modifier.fillMaxWidth(1f),
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1
                                        )
                                        Text(
                                            text = component.content,
                                            style = MaterialTheme.typography.h6,
                                            modifier = Modifier.fillMaxWidth(1f),
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1
                                        )
                                    }
                                    
                                    Spacer(modifier = Modifier.width(padding))
                                    
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_proceed),
                                        contentDescription = null,
                                    )
                                }
                            }
                            ComponentType.Link -> {

                            }
                            ComponentType.Video -> {
                                // pending...
                            }
                            ComponentType.Image -> {
                                Image(component = component)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NotePreview() {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.HOUR, 5)

    val titleComponent = Component().apply {
        title = "Instagram Content Plan"
        type = ComponentType.Text
    }

    val text = Component().apply {
        content = "I hope you enjoy it. Feel free to share your thoughts in the following section."
        type = ComponentType.Text
    }

    val note = Note().apply {
        this.title = History(titleComponent)
        this.content.add(History(text))
        this.content.add(History(
            Component(
                this, calendar.time, "Meeting with Anna"
            )
        ))
        this.isImportant = true
        this.tags = realmListOf("project")
    }

    WriterMeTheme {
        Note(note)
    }
}