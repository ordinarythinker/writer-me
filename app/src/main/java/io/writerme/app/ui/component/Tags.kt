package io.writerme.app.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.backgroundGrey
import io.writerme.app.ui.theme.light
import io.writerme.app.ui.theme.strokeLight

@OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun TagsBar(
    tags: List<String>,
    addNewTag: (String) -> Unit,
    deleteTag: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember {
        mutableStateOf("")
    }

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
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.light,
                modifier = Modifier.padding(top = padding, end = padding)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                for (tag in tags) {
                    Chip(
                        onClick = { },
                        shape = RoundedCornerShape(padding),
                        enabled = true,
                        colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colors.backgroundGrey)
                    ) {
                        Text(
                            text = "#$tag",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.light
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colors.light,
                            modifier = Modifier.clickable { deleteTag(tag) }
                        )
                    }

                    Spacer(modifier = Modifier.width(padding))
                }

                BasicTextField(
                    modifier = Modifier
                        .padding(top = padding)
                        .width(150.dp)
                        .onKeyEvent {
                            if (it.type == KeyEventType.KeyDown || it.key == Key.Enter) {
                                addNewTag(text)
                                true
                            } else false
                        },
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    decorationBox = { innerTextField ->
                        Row {
                            if (text.isEmpty()) {
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
}

@Preview(showBackground = true)
@Composable
fun TagsBarPreview() {
    WriterMeTheme {
        TagsBar(listOf("traveling", "unstoppable", "dreamer"), {}, {})
    }
}