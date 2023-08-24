package io.writerme.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme

@Composable
fun Counter(value: Int, onChange: (increase: Boolean) -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    dimensionResource(id = R.dimen.big_radius)
                )
            )
            .background(Color.White)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowLeft,
            contentDescription = stringResource(id = R.string.increase),
            modifier = Modifier
                .padding(start = 4.dp, top = 4.dp, bottom = 4.dp)
                .clickable {
                    onChange(false)
                }
        )

        Text(
            text = "$value",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(16.dp, 6.dp)
        )

        Icon(
            imageVector = Icons.Default.ArrowRight,
            contentDescription = stringResource(id = R.string.decrease),
            modifier = Modifier
                .padding(top = 4.dp, bottom = 4.dp, end = 4.dp)
                .clickable {
                    onChange(true)
                }
        )
    }
}

@Preview
@Composable
fun CounterPreview() {
    WriterMeTheme {
        Counter(1) { _: Boolean -> }
    }
}