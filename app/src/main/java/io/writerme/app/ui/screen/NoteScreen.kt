package io.writerme.app.ui.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.BottomAppBar
import androidx.compose.material.DropdownMenu
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.Lifecycle
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
import io.writerme.app.ui.theme.dropdownBackground
import io.writerme.app.ui.theme.light
import io.writerme.app.utils.OnLifecycleEvent
import io.writerme.app.utils.copyComponentContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    noteState: StateFlow<NoteState>,
    toggleHistoryMode: () -> Unit,
    toggleTopBarDropdownVisibility: () -> Unit,
    addCoverImage: (String) -> Unit,
    onTitleChange: (String) -> Unit,
    showHashtagBar: (Boolean) -> Unit,
    addNewTag: (String) -> Unit,
    deleteTag: (String) -> Unit,
    modifyHistory: (History, Component) -> Unit,
    saveChanges: () -> Unit,
    onComponentChange: (Component) -> Unit,
    addNewCheckBox: (Int) -> Unit,
    navigateBack: () -> Unit,
    addImageSection: (String) -> Unit,
    showDropdown: (Int) -> Unit,
    dismissDropDown: () -> Unit,
    toggleDropDownHistoryMode: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    val focusRequester = remember { FocusRequester() }

    val state = noteState.collectAsStateWithLifecycle()

    OnLifecycleEvent(onEvent = { _, event ->
        if (event == Lifecycle.Event.ON_PAUSE) {
            saveChanges()
        }
    })

    val context = LocalContext.current

    val addCoverImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            addCoverImage(uri.toString())
        }
    )

    val addImageComponentPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            addImageSection(uri.toString())
        }
    )

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
                        IconButton(onClick = navigateBack) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = stringResource(id = R.string.back_button),
                                tint = MaterialTheme.colors.light
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = toggleTopBarDropdownVisibility) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_more),
                                contentDescription = stringResource(id = R.string.more),
                                tint = MaterialTheme.colors.light
                            )
                        }

                        MaterialTheme(
                            colors = MaterialTheme.colors.copy(
                                surface = MaterialTheme.colors.dropdownBackground,
                                background = Color.Blue
                            ),
                            shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(
                                dimensionResource(id = R.dimen.small_radius)
                            ))
                        ) {
                            DropdownMenu(
                                expanded = state.value.isTopBarDropdownVisible,
                                onDismissRequest = toggleTopBarDropdownVisibility,
                                properties = PopupProperties()
                            ) {

                                DropdownMenuItem(
                                    onClick = toggleHistoryMode,
                                    enabled = true
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.history_mode),
                                        style = MaterialTheme.typography.subtitle1,
                                        color = MaterialTheme.colors.light
                                    )

                                    Spacer(modifier = Modifier.width(width = 8.dp))

                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_history),
                                        contentDescription = stringResource(id = R.string.history_mode),
                                        tint = MaterialTheme.colors.light
                                    )
                                }
                            }
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
                        // pending features

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
                            if (!state.value.isTagsBarVisible) {
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

                        IconButton(onClick = {
                            // TODO: add link
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_link),
                                contentDescription = stringResource(id = R.string.add_link_button),
                                tint = MaterialTheme.colors.light
                            )
                        }

                        IconButton(onClick = {
                            // pending: add voice
                            Toast.makeText(context, context.resources.getString(R.string.pending), Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_microphone),
                                contentDescription = stringResource(id = R.string.add_voice_note_button),
                                tint = MaterialTheme.colors.light
                            )
                        }

                        IconButton(onClick = {
                            // pending: add task
                            Toast.makeText(context, context.resources.getString(R.string.pending), Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_checked),
                                contentDescription = stringResource(id = R.string.add_tast_button),
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colors.light
                            )
                        }

                        IconButton(onClick = {
                            addImageComponentPicker.launch("image/*")
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = stringResource(id = R.string.add_media_button),
                                tint = MaterialTheme.colors.light
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            val note = state.value.note
            val padding = dimensionResource(id = R.dimen.screen_padding)

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        start = padding, top = padding, end = padding,
                        bottom = paddingValues.calculateBottomPadding() + padding
                    )
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
                                onClick = {
                                    addCoverImagePicker.launch("image/*")
                                }
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

                item {
                    AnimatedVisibility(visible = state.value.note.tags.isNotEmpty()) {
                        Column {
                            TagsBar(
                                tags = state.value.note.tags,
                                addNewTag = addNewTag,
                                deleteTag = deleteTag
                            )

                            Spacer(modifier = Modifier.height(padding))
                        }
                    }
                }

                itemsIndexed(
                    items = note.content,
                    itemContent = {currentIndex, item ->
                        val newest = item.newest()

                        newest?.let { component ->
                            val isExpanded = currentIndex == state.value.expandedDropdownId

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
                                                onValueChange = onComponentChange,
                                            )
                                        }
                                        ComponentType.Checkbox -> {
                                            Checkbox(
                                                component = component,
                                                modifier = Modifier.padding(start = padding),
                                                onValueChange = onComponentChange,
                                                onAddNewCheckbox = {
                                                    addNewCheckBox(currentIndex)
                                                }
                                            )
                                        }
                                        ComponentType.Voice -> {}
                                        ComponentType.Task -> {
                                            Task(
                                                task = component,
                                                onClick = { /*TODO*/ },
                                                onValueChange = onComponentChange
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
                                            // pending feature
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
                                    ExposedDropdownMenu(
                                        expanded = isExpanded,
                                        onDismissRequest = { dismissDropDown() },
                                        scrollState = rememberScrollState()
                                    ) {
                                        if (state.value.isDropDownInHistoryMode) {
                                            item.changes.forEach { component ->
                                                DropdownMenuItem(onClick = {
                                                    modifyHistory(item, component)
                                                    dismissDropDown()
                                                }) {
                                                    when (component.type) {
                                                        ComponentType.Text,
                                                        ComponentType.Checkbox,
                                                        ComponentType.Task -> {
                                                            Text(
                                                                text = component.content,
                                                                style = MaterialTheme.typography.body1
                                                            )
                                                        }

                                                        ComponentType.Link -> {
                                                            Text(
                                                                text = component.url,
                                                                style = MaterialTheme.typography.body1,
                                                                modifier = Modifier.fillMaxWidth(),
                                                                maxLines = 1,
                                                                overflow = TextOverflow.Ellipsis
                                                            )
                                                        }

                                                        ComponentType.Image -> {
                                                            io.writerme.app.ui.component.Image(
                                                                component = component
                                                            )
                                                        }

                                                        ComponentType.Voice -> {
                                                            // pending
                                                        }
                                                        ComponentType.Video -> {
                                                            // pending feature
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            val clipboardManager = LocalClipboardManager.current

                                            Column {
                                                DropdownMenuItem(onClick = {
                                                    clipboardManager.copyComponentContent(
                                                        component = component, context = context
                                                    )
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
                                                            painter = painterResource(id = R.drawable.ic_history),
                                                            contentDescription = stringResource(id = R.string.copy),
                                                            modifier = Modifier.size(20.dp),
                                                            tint = Color.DarkGray
                                                        )
                                                    }
                                                }

                                                DropdownMenuItem(onClick = toggleDropDownHistoryMode) {
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
                                }
                            }

                            Spacer(modifier = Modifier.height(padding))
                        }
                    }
                )
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
                History(text),
                //History(checkbox), History(task), History(image), History(image)
            )
        )

        tags.addAll(listOf("stories", "work"))
    }

    val noteState = NoteState(note = note)
    val state = MutableStateFlow(noteState)

    WriterMeTheme {
        NoteScreen(noteState = state, {}, {}, {}, {}, {}, {}, {},
            { _, _ ->}, {}, {}, { _ ->}, {}, { _ ->}, {}, {}, {})
    }
}