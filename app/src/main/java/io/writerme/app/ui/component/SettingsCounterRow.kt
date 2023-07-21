package io.writerme.app.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    range: Pair<Int, Int>,
    initialValue: Int,
    onChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.screen_padding), 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = stringId),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.light
        )

        Counter(
            initialValue = initialValue,
            range = range,
            onChange = { }
        )
    }
}

@Preview
@Composable
fun SettingsCounterRowPreview() {
    WriterMeTheme {
        SettingsCounterRow(
            stringId = R.string.number_of_text_changes,
            range = 1 to 5,
            initialValue = 5,
            onChange = {}
        )
    }
}