package io.writerme.app.ui.screen

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.writerme.app.R
import io.writerme.app.ui.component.ProfileImage
import io.writerme.app.ui.component.SettingsCounterRow
import io.writerme.app.ui.component.SettingsSectionTitle
import io.writerme.app.ui.state.SettingsState
import io.writerme.app.ui.theme.*
import io.writerme.app.utils.Const
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    uiState: StateFlow<SettingsState>,
    onLanguageChange: (String) -> Unit,
    onDarkModeChange: (Boolean) -> Unit,
    onTermsClick: () -> Unit,
    onCounterChange: (String, Boolean) -> Unit,
    updateProfileImage: (String) -> Unit,
    dismissScreen: () -> Unit
) {
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val state = uiState.collectAsStateWithLifecycle()

    val updateProfileImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { updateProfileImage(it.toString()) }
        }
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_main),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(id = R.string.background_image),
            modifier = Modifier.fillMaxSize()
        )

        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                    title = {
                        Text(
                            text = stringResource(id = R.string.settings),
                            style = MaterialTheme.typography.h2,
                            color = MaterialTheme.colors.light
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = dismissScreen) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = stringResource(id = R.string.back_button),
                                tint = MaterialTheme.colors.light
                            )
                        }
                    }
                )
            }
        ) {
            val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                val screenPadding = dimensionResource(id = R.dimen.screen_padding)

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(screenPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AnimatedVisibility(visible = true) {
                        if (state.value.profilePictureUrl.isNotEmpty()) {
                            ProfileImage(
                                url = state.value.profilePictureUrl,
                                onClick = { updateProfileImagePicker.launch("image/*") }
                            )
                        } else {
                            IconButton(
                                modifier = Modifier
                                    .clip(shape)
                                    .background(MaterialTheme.colors.backgroundGrey)
                                    .padding(4.dp, 12.dp)
                                    .shadow(dimensionResource(id = R.dimen.shadow), shape),
                                onClick = {
                                    updateProfileImagePicker.launch("image/*")
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_camera),
                                    contentDescription = stringResource(id = R.string.add_cover_image_button),
                                    modifier = Modifier.size(40.dp),
                                    tint = MaterialTheme.colors.light
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(0.6f)
                            .padding(screenPadding, 0.dp)
                    ) {
                        Text(
                            text = state.value.fullName,
                            style = MaterialTheme.typography.h1,
                            color = MaterialTheme.colors.light
                        )

                        Text(
                            text = state.value.email,
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.light,
                            modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
                        )
                    }
                }

                // TODO: add the dialog to edit email, photo and the full name

                Card(
                    modifier = Modifier.padding(screenPadding, 8.dp),
                    shape = RoundedCornerShape(25.dp),
                    elevation = 20.dp,
                    backgroundColor = MaterialTheme.colors.cardBackground
                ) {
                    Column {
                        SettingsSectionTitle(
                            titleRes = R.string.changes_history,
                            iconRes = R.drawable.ic_history
                        )

                        Divider(
                            modifier = Modifier.padding(screenPadding, 0.dp, screenPadding, screenPadding),
                            color = MaterialTheme.colors.strokeLight
                        )

                        Text(
                            text = stringResource(id = R.string.changes_history_description),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(screenPadding, 8.dp, screenPadding, 20.dp),
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.light
                        )

                        SettingsCounterRow(
                            stringId = R.string.number_of_text_changes,
                            value = state.value.textChanges,
                            increaseValueId = R.string.increase_number_of_text_changes,
                            decreaseValueId = R.string.decrease_number_of_text_changes,
                            onChange = {
                                onCounterChange(Const.TEXT_CHANGES_HISTORY_KEY, it)
                            })

                        SettingsCounterRow(
                            stringId = R.string.number_of_voice_changes,
                            value = state.value.voiceChanges,
                            increaseValueId = R.string.increase_number_of_voice_changes,
                            decreaseValueId = R.string.decrease_number_of_voice_changes,
                            onChange = {
                                onCounterChange(Const.VOICE_CHANGES_HISTORY_KEY, it)
                            })

                        SettingsCounterRow(
                            stringId = R.string.number_of_tasks_changes,
                            value = state.value.taskChanges,
                            increaseValueId = R.string.increase_number_of_tasks_changes,
                            decreaseValueId = R.string.decrease_number_of_tasks_changes,
                            onChange = {
                                onCounterChange(Const.TASK_CHANGES_HISTORY_KEY, it)
                            })

                        SettingsCounterRow(
                            stringId = R.string.number_of_media_changes,
                            value = state.value.mediaChanges,
                            increaseValueId = R.string.increase_number_of_media_changes,
                            decreaseValueId = R.string.decrease_number_of_media_changes,
                            onChange = {
                                onCounterChange(Const.MEDIA_CHANGES_HISTORY_KEY, it)
                            })

                        SettingsCounterRow(
                            stringId = R.string.number_of_link_changes,
                            value = state.value.linkChanges,
                            increaseValueId = R.string.increase_number_of_link_changes,
                            decreaseValueId = R.string.decrease_number_of_link_changes,
                            onChange = {
                                onCounterChange(Const.LINK_CHANGES_HISTORY_KEY, it)
                            })

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Card(
                    shape = shape,
                    modifier = Modifier
                        .padding(screenPadding, 8.dp)
                        .shadow(dimensionResource(id = R.dimen.shadow), shape),
                    backgroundColor = Color.Transparent
                ) {
                    Box(
                       modifier = Modifier
                           .fillMaxWidth()
                           .wrapContentHeight()
                    ) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(MaterialTheme.colors.cardBackground)
                                .blur(dimensionResource(id = R.dimen.blur_radius))
                        )

                        Column {
                            SettingsSectionTitle(
                                titleRes = R.string.appearance
                            )

                            Divider(
                                modifier = Modifier.padding(screenPadding, 0.dp, screenPadding, screenPadding),
                                color = MaterialTheme.colors.strokeLight
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(screenPadding, 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(id = R.string.language),
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.light
                                )

                                var isLanguagesListExpanded by remember {
                                    mutableStateOf(false)
                                }

                                ExposedDropdownMenuBox(
                                    expanded = isLanguagesListExpanded,
                                    onExpandedChange = { isLanguagesListExpanded = !isLanguagesListExpanded },
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(48.dp)
                                ) {
                                    val radius = dimensionResource(id = R.dimen.big_radius)
                                    TextField(
                                        value = state.value.currentLanguage,
                                        onValueChange = onLanguageChange,
                                        readOnly = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = isLanguagesListExpanded
                                            )
                                        },
                                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            backgroundColor = MaterialTheme.colors.light,

                                            ),
                                        shape = RoundedCornerShape(radius),
                                        textStyle = MaterialTheme.typography.body2
                                    )

                                    MaterialTheme(
                                        colors = MaterialTheme.colors.copy(
                                            surface = MaterialTheme.colors.light,
                                            background = Color.Blue
                                        ),
                                        shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(radius))
                                    ) {
                                        ExposedDropdownMenu(
                                            expanded = isLanguagesListExpanded,
                                            onDismissRequest = { isLanguagesListExpanded = false }
                                        ) {
                                            state.value.languages.forEach { selectedOption ->
                                                DropdownMenuItem(onClick = {
                                                    onLanguageChange(selectedOption)
                                                    isLanguagesListExpanded = false
                                                }) {
                                                    Text(
                                                        text = selectedOption,
                                                        style  = MaterialTheme.typography.body2
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }


                            /*Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(screenPadding, 8.dp)
                                    .clickable {

                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(id = R.string.dark_mode),
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.light
                                )

                                Switch(
                                    checked = state.value.isDarkMode,
                                    onCheckedChange = { onDarkModeChange(it) }
                                )
                            }*/
                        }
                    }
                }

                Card(
                    modifier = Modifier.padding(screenPadding, 8.dp),
                    shape = RoundedCornerShape(25.dp),
                    elevation = 16.dp,
                    backgroundColor = MaterialTheme.colors.cardBackground
                ) {
                    Column {
                        SettingsSectionTitle(
                            titleRes = R.string.help,
                            iconRes = R.drawable.ic_help
                        )

                        Divider(
                            modifier = Modifier.padding(screenPadding, 0.dp, screenPadding, 0.dp),
                            color = MaterialTheme.colors.strokeLight
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(screenPadding)
                                .clickable { onTermsClick() },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = stringResource(id = R.string.terms),
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.light
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.ic_globe),
                                contentDescription = stringResource(id = R.string.globe_icon),
                                tint = MaterialTheme.colors.light
                            )
                        }
                    }
                }


                val text = stringResource(id = R.string.copyright)
                val year = Calendar.getInstance().get(Calendar.YEAR)

                Text(
                    text = String.format(text, year),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val state = SettingsState(
        currentLanguage = "English",
        fullName = "Florian Hermes",
        email = "florian.hermes@email.com",
        profilePictureUrl = "",
        languages = listOf("English", "Deutsch", "Українська"),
    )


    WriterMeTheme {
        SettingsScreen(
            MutableStateFlow(state),
            {}, {}, {}, {_, _, ->}, {}, {}
        )
    }
}
