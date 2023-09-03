package io.writerme.app.ui.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.BottomAppBar
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.writerme.app.R
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.model.History
import io.writerme.app.data.model.Note
import io.writerme.app.ui.component.Checkbox
import io.writerme.app.ui.component.Link
import io.writerme.app.ui.component.NoteText
import io.writerme.app.ui.component.TagsBar
import io.writerme.app.ui.component.Task
import io.writerme.app.ui.state.NoteState
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.backgroundGrey
import io.writerme.app.ui.theme.light
import io.writerme.app.utils.copyComponentContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    noteState: StateFlow<NoteState>,
    onTitleChange: (String) -> Unit,
    showHashtagBar: (Boolean) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    val focusRequester = remember { FocusRequester() }

    val state = noteState.collectAsStateWithLifecycle()

    var expandedDropdownId by remember {
        mutableIntStateOf(-1)
    }

    val showDropdown: (id: Int) -> Unit = {
        expandedDropdownId = it
    }

    val dismissDropDown: () -> Unit = {
        expandedDropdownId = -1
    }

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
        ) {
            val note = state.value.note
            val padding = dimensionResource(id = R.dimen.screen_padding)

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = padding, top = padding, end = padding, bottom = 70.dp)
            ) {
                item {
                    val title = note.title?.newest()

                    if (note.cover!= null && note.cover!!.isNotEmpty()) {
                        val image = note.cover!!.newest()

                        Column(
                            modifier = Modifier.padding(bottom = padding)
                        ) {
                            image?.let {
                                io.writerme.app.ui.component.Image(
                                    component = it,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = padding)
                                )
                            }

                            title?.let {
                                BasicTextField(
                                    value = title.title,
                                    onValueChange = onTitleChange,
                                    textStyle = MaterialTheme.typography.h1.copy(color = MaterialTheme.colors.light),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = dimensionResource(id = R.dimen.screen_padding_big))
                                )
                            }
                        }
                    } else {
                        val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = padding)
                        ) {
                            IconButton(
                                modifier = Modifier
                                    .clip(shape)
                                    .background(MaterialTheme.colors.backgroundGrey)
                                    .padding(4.dp, 12.dp)
                                    .shadow(dimensionResource(id = R.dimen.shadow), shape),
                                onClick = { /*TODO*/ }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_camera),
                                    contentDescription = stringResource(id = R.string.add_cover_image_button),
                                    modifier = Modifier.size(40.dp),
                                    tint = MaterialTheme.colors.light
                                )
                            }

                            val text = title?.title ?: ""
                            BasicTextField(
                                value = text,
                                onValueChange = onTitleChange,
                                textStyle = MaterialTheme.typography.h1.copy(color = MaterialTheme.colors.light),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = padding)
                            )
                        }
                    }
                }

                itemsIndexed(
                    items = note.content,
                    itemContent = {currentIndex, item ->
                        val newest = item.newest()

                        newest?.let { component ->
                            val isExpanded = currentIndex == expandedDropdownId

                            ExposedDropdownMenuBox(
                                expanded = isExpanded,
                                onExpandedChange = { dismissDropDown() }
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    AnimatedVisibility(
                                        visible = state.value.isHistoryMode,
                                        modifier = Modifier.align(Alignment.End)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_more),
                                            contentDescription = stringResource(id = R.string.more),
                                            tint = MaterialTheme.colors.light,
                                            modifier = Modifier
                                                .padding(0.dp, 0.dp, 0.dp, 8.dp)
                                                .size(20.dp)
                                                .clickable {
                                                    showDropdown(currentIndex)
                                                }
                                        )
                                    }

                                    when (component.type) {
                                        ComponentType.Text -> {
                                            NoteText(
                                                component = component,
                                                onValueChange = { /*TODO*/ },
                                            )
                                        }
                                        ComponentType.Checkbox -> {
                                            Checkbox(
                                                component = component,
                                                modifier = Modifier.padding(start = padding)
                                            )
                                            // TODO: possible problem since Checkbox is not editable
                                            // TODO: make it editable, onValueChange
                                        }
                                        ComponentType.Voice -> {}
                                        ComponentType.Task -> {
                                            Task(
                                                task = component,
                                                onClick = { /*TODO*/ }
                                            )
                                        }
                                        ComponentType.Link -> {
                                            Link(
                                                link = component,
                                                onClick = {},
                                            )
                                            // TODO: link is not editable, though it should be
                                        }
                                        ComponentType.Video -> {
                                            // TODO: pending feature
                                        }
                                        ComponentType.Image -> {
                                            io.writerme.app.ui.component.Image(
                                                component = component
                                            )
                                        }
                                    }
                                }

                                MaterialTheme(
                                    colors = MaterialTheme.colors.copy(
                                        surface = MaterialTheme.colors.light,
                                        background = Color.Blue
                                    ),
                                    shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(
                                        dimensionResource(id = R.dimen.small_radius)
                                    ))
                                ) {
                                    val context = LocalContext.current

                                    ExposedDropdownMenu(
                                        expanded = isExpanded,
                                        onDismissRequest = { dismissDropDown() },
                                        scrollState = rememberScrollState()
                                    ) {
                                        DropdownMenuItem(onClick = {
                                            context.copyComponentContent(component)

                                            dismissDropDown()
                                        }) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = stringResource(id = R.string.copy),
                                                    style  = MaterialTheme.typography.body1
                                                )

                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_copy),
                                                    contentDescription = stringResource(id = R.string.copy),
                                                    modifier = Modifier.size(20.dp),
                                                    tint = Color.DarkGray
                                                )
                                            }
                                        }

                                        DropdownMenuItem(onClick = {

                                            dismissDropDown()
                                        }) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = stringResource(id = R.string.history),
                                                    style  = MaterialTheme.typography.body1
                                                )

                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_history),
                                                    contentDescription = stringResource(id = R.string.copy),
                                                    modifier = Modifier.size(20.dp),
                                                    tint = Color.DarkGray
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(padding))
                        }
                    }
                )

                item {
                    TagsBar(
                        tags = listOf("traveling", "outdoors"),
                        addNewTag = {},
                        deleteTag = {}
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NoteScreenPreview() {
    val note = Note()

    val text = Component().apply {
        type = ComponentType.Text
        content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry..."
    }

    val checkbox = Component().apply {
        type = ComponentType.Checkbox
        content = "Complete writing post for Instagram"
        isChecked = true
    }

    val task = Component().apply {
        content = "Meeting with Anna"
        time = Date()
        type = ComponentType.Task
    }

    val image = Component().apply {
        type = ComponentType.Image
        imageUrl = ""
    }

    val link = Component().apply {
        type = ComponentType.Link
        title = "Best resort in Italy you ever dreamed about"
        url = ""
    }

    note.apply {
        title = History()
        title!!.push(
            component = Component().apply {
                title = "Instagram Content Plan for Beginner"
                type = ComponentType.Text
            }
        )

        /*cover.push(
            component = Component().apply {
                imageUrl = "some url..."
                type = ComponentType.Image
            }
        )*/

        content.addAll(
            listOf(
                History(text)
                //History(checkbox), History(task), History(image)
            )
        )

        tags.addAll(listOf("stories", "work"))
    }

    val noteState = NoteState(note = note)
    val state = MutableStateFlow(noteState)

    WriterMeTheme {
        NoteScreen(noteState = state, {}, {})
    }
}