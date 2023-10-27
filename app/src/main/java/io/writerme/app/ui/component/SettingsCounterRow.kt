package io.writerme.app.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.light

@Composable
fun SettingsCounterRow(
    stringId: Int,
    value: Int,
    increaseValueId: Int,
    decreaseValueId: Int,
    onChange: (increase: Boolean) -> Unit
) {
    val padding = dimensionResource(id = R.dimen.screen_padding)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = stringId),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.light,
            modifier = Modifier.weight(1f)
        )
        
        Spacer(modifier = Modifier.width(padding))

        Counter(
            value = value,
            onChange = onChange,
            increaseValueId = increaseValueId,
            decreaseValueId = decreaseValueId
        )
    }
}

@Preview
@Composable
fun SettingsCounterRowPreview() {
    WriterMeTheme {
        SettingsCounterRow(
            stringId = R.string.number_of_text_changes,
            value = 5,
            increaseValueId = 0,
            decreaseValueId = 0,
            onChange = {}
        )
    }
}