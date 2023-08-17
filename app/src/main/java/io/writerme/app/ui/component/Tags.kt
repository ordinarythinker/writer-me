package io.writerme.app.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.strokeLight

@OptIn(ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TagsBar(
    tags: List<String>,
    addNewTag: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.strokeLight,
            thickness = 1.dp
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow {
            for (tag in tags) {
                // TODO: here will be chips
            }


            // TODO: add here the spacing modifier
            BasicTextField(
                modifier = Modifier.onKeyEvent {
                    if (it.type == KeyEventType.KeyDown || it.key == Key.Enter) {
                        addNewTag(text)
                        true
                    } else false
                },
                value = text,
                onValueChange = {
                    text = it
                },
                decorationBox = {
                    // TODO: here will be the placeholder
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TagsBarPreview() {
    WriterMeTheme {
        TagsBar(listOf(), {})
    }
}