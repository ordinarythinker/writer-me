package io.writerme.app.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dokar.chiptextfield.BasicChipTextField
import com.dokar.chiptextfield.Chip
import com.dokar.chiptextfield.ChipTextFieldDefaults
import com.dokar.chiptextfield.rememberChipTextFieldState
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.backgroundGrey
import io.writerme.app.ui.theme.light
import io.writerme.app.ui.theme.strokeLight


@Composable
fun TagsBar(
    tags: List<String>,
    addNewTag: (String) -> Unit,
    deleteTag: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val initialText = stringResource(id = R.string.add_hashtags)
    var text by remember {
        mutableStateOf(initialText)
    }

    val state = rememberChipTextFieldState<Chip>(
        chips = tags.map { Chip(it) }
    )

    val padding = dimensionResource(id = R.dimen.screen_padding)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = padding)
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.strokeLight,
            thickness = 1.dp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.tags),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.light,
                modifier = Modifier.padding(top = 13.dp, end = padding)
            )

            BasicChipTextField(
                modifier = Modifier.fillMaxWidth(),
                state = state,
                value = text,
                onValueChange = { text = it },
                onSubmit = {
                    addNewTag(it)
                    Chip(it)
                },
                textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.light),
                chipStyle = ChipTextFieldDefaults.chipStyle(
                    shape = RoundedCornerShape(padding),
                    focusedBackgroundColor = MaterialTheme.colors.backgroundGrey,
                    unfocusedBackgroundColor = MaterialTheme.colors.backgroundGrey,
                    focusedTextColor = MaterialTheme.colors.light,
                    unfocusedTextColor = MaterialTheme.colors.light,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                chipTrailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colors.light,
                        modifier = Modifier.clickable { deleteTag(it.text) }.padding(end = 4.dp)
                    )
                },
                decorationBox = { innerTextField ->
                    Row {
                        if (text.isEmpty() && state.chips.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.add_hashtags),
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.light,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TagsBarPreview() {
    WriterMeTheme {
        TagsBar(listOf("traveling", "unstoppable", "dreamer"), {}, {})
    }
}