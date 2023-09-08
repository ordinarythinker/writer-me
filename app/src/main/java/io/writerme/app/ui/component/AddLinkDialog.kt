package io.writerme.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.fieldDark
import io.writerme.app.ui.theme.light
import io.writerme.app.ui.theme.strokeLight

@Composable
fun AddLinkDialogBody(
    addLink: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Column {
        val padding = dimensionResource(id = R.dimen.screen_padding)
        val fieldShape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

        var url by remember { mutableStateOf("") }

        val backgroundModifier = Modifier
            .clip(fieldShape)
            .border(
                dimensionResource(id = R.dimen.field_border_width),
                MaterialTheme.colors.strokeLight,
                fieldShape
            )
            .background(MaterialTheme.colors.fieldDark)
            .padding(padding, 0.dp)
            .height(40.dp)

        BasicTextField(
            value = url,
            maxLines = 1,
            onValueChange = { url = it },
            modifier = backgroundModifier.fillMaxWidth(),
            singleLine = true,
            textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.light),
            decorationBox = { innerTextField ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (url.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.url),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.light
                        )
                    }
                    innerTextField()
                }
            },
        )

        Spacer(modifier = Modifier.height(padding))

        Text(
            text = stringResource(id = R.string.add),
            modifier = backgroundModifier
                .wrapContentHeight()
                .padding(20.dp, 0.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    addLink(url)
                    onDismiss()
                },
            color = MaterialTheme.colors.light
        )
    }
}

@Preview
@Composable
fun AddLinkDialogBodyPreview() {
    WriterMeTheme {
        AddLinkDialogBody({}, {})
    }
}