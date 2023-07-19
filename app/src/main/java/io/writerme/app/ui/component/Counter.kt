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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme

@Composable
fun Counter(initialValue: Int, range: Pair<Int, Int>, onChange: (Int) -> Unit) {
    var currentValue by remember { mutableStateOf(initialValue) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(Color.White)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowLeft,
            contentDescription = stringResource(id = R.string.increase),
            modifier = Modifier.clickable {
                if (currentValue > range.first) {
                    currentValue--
                    onChange(currentValue)
                }
            }
        )

        Text(
            text = "$initialValue",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(16.dp, 6.dp)
        )

        Icon(
            imageVector = Icons.Default.ArrowRight,
            contentDescription = stringResource(id = R.string.decrease),
            modifier = Modifier.clickable {
                if (currentValue < range.first) {
                    currentValue++
                    onChange(currentValue)
                }
            }
        )
    }
}

@Preview
@Composable
fun CounterPreview() {
    WriterMeTheme {
        Counter(1, 1 to 5) { a: Int -> }
    }
}