package io.writerme.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.dialogBackground
import io.writerme.app.ui.theme.light
import io.writerme.app.ui.theme.strokeLight

@Composable
fun TitledDialog(
    title: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

    val padding = dimensionResource(id = R.dimen.screen_padding)

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            shape = shape,
            modifier = modifier
                .wrapContentHeight()
                .shadow(dimensionResource(id = R.dimen.shadow), shape)
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(MaterialTheme.colors.dialogBackground)
                        .blur(dimensionResource(id = R.dimen.blur_radius))
                )

                Column {
                    Text(
                        modifier = Modifier.padding(0.dp, 8.dp, 0.dp, padding),
                        text = title,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.light
                    )

                    Divider(
                        thickness = 1.dp,
                        color = MaterialTheme.colors.strokeLight
                    )

                    Spacer(modifier = Modifier.height(padding))

                    content()
                }
            }
        }
    }
}

@Preview
@Composable
fun TitledDialogPreview() {
    WriterMeTheme {
        TitledDialog(
            "", {},
            content = {}
        )
    }
}