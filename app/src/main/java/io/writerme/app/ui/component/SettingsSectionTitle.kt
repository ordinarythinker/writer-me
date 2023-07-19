package io.writerme.app.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.textLight

@Composable
fun SettingsSectionTitle(titleRes: Int, iconRes: Int? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.screen_padding),
                dimensionResource(id = R.dimen.screen_padding)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.textLight
        )

        iconRes?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = stringResource(id = R.string.icon),
                tint = MaterialTheme.colors.textLight
            )
        }
    }
}

@Preview
@Composable
fun SettingsSectionTitlePreview() {
    WriterMeTheme {
        SettingsSectionTitle(titleRes = R.string.changes_history, iconRes = R.drawable.ic_history)
    }
}