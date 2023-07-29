package io.writerme.app.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(uiState: StateFlow<SettingsState>) {
    val scrollState = rememberScrollState()
    val state = uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(scrollState, Orientation.Vertical)
            .paint(
                painterResource(id = R.drawable.background_main),
                contentScale = ContentScale.Crop
            )
    ) {
        val screenPadding = dimensionResource(id = R.dimen.screen_padding)

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(screenPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(url = state.value.profilePictureUrl)

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
                    range = 1 to Const.TEXT_CHANGES_HISTORY,
                    initialValue = Const.TEXT_CHANGES_HISTORY,
                    onChange = {
                        state.value.onCounterChange(it, Const.TEXT_CHANGES_HISTORY_KEY)
                    })

                SettingsCounterRow(
                    stringId = R.string.number_of_voice_changes,
                    range = 1 to Const.VOICE_CHANGES_HISTORY,
                    initialValue = Const.VOICE_CHANGES_HISTORY, // TODO: get it from SharedPreferences
                    onChange = {
                        state.value.onCounterChange(it, Const.VOICE_CHANGES_HISTORY_KEY)
                    })

                SettingsCounterRow(
                    stringId = R.string.number_of_tasks_changes,
                    range = 1 to Const.TASK_CHANGES_HISTORY,
                    initialValue = Const.TASK_CHANGES_HISTORY, // TODO: get it from SharedPreferences
                    onChange = {
                        state.value.onCounterChange(it, Const.TASK_CHANGES_HISTORY_KEY)
                    })

                SettingsCounterRow(
                    stringId = R.string.number_of_media_changes,
                    range = 1 to Const.MEDIA_CHANGES_HISTORY,
                    initialValue = Const.MEDIA_CHANGES_HISTORY, // TODO: get it from SharedPreferences
                    onChange = {
                        state.value.onCounterChange(it, Const.MEDIA_CHANGES_HISTORY_KEY)
                    })

                Spacer(modifier = Modifier.height(8.dp))
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
                            onValueChange = state.value.onLanguageChange,
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
                            shape = if (isLanguagesListExpanded) {
                                RoundedCornerShape(radius, radius, 0.dp, 0.dp)
                            } else {
                                RoundedCornerShape(radius)
                            },
                            textStyle = MaterialTheme.typography.body2
                        )

                        MaterialTheme(
                            colors = MaterialTheme.colors.copy(
                                surface = MaterialTheme.colors.light,
                                background = Color.Blue
                            ),
                            shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(0.dp, 0.dp, radius, radius))
                        ) {
                            ExposedDropdownMenu(
                                /*modifier = Modifier.background(
                                    MaterialTheme.colors.light, RoundedCornerShape(0.dp, 0.dp, radius, radius)
                                ),*/
                                expanded = isLanguagesListExpanded,
                                onDismissRequest = { isLanguagesListExpanded = false }
                            ) {
                                state.value.languages.forEach { selectedOption ->
                                    DropdownMenuItem(onClick = {
                                        state.value.onLanguageChange(selectedOption)
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


                Row(
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
                        onCheckedChange = { state.value.onDarkModeChange(it) }
                    )
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
                        .clickable { state.value.onTermsClick() },
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

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val state = SettingsState(
        currentLanguage = "English",
        languages = listOf("English", "Deutsch", "Українська"),
        onLanguageChange = {},
        onDarkModeChange = {},
        onTermsClick = {},
        onCounterChange = { _, _ ->}
    )
    state.apply {
        fullName = "Florian Hermes"
        email = "florian.hermes@email.com"
    }

    WriterMeTheme {
        SettingsScreen(MutableStateFlow(state))
    }
}
