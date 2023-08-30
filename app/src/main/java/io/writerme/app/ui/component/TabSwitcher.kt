package io.writerme.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.light
import io.writerme.app.ui.theme.lightTransparent
import io.writerme.app.ui.theme.strokeLight

@Composable
fun TabSwitcher(
    tabs: List<String>,
    chosen: String,
    onItemChosen: (String) -> Unit
) {
    val radius = dimensionResource(id = R.dimen.small_radius)
    val shape = RoundedCornerShape(radius)

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .background(MaterialTheme.colors.lightTransparent, shape)
            .border(1.dp, MaterialTheme.colors.strokeLight, shape)
            .padding(vertical = 4.dp)
    ) {
        tabs.forEach {
            val modifier = if (chosen == it) {
                Modifier
                    .width(120.dp)
                    .padding(horizontal = 4.dp)
                    .background(MaterialTheme.colors.lightTransparent, shape)
                    .border(1.dp, MaterialTheme.colors.strokeLight, shape)
                    .padding(vertical = 4.dp)
            } else {
                Modifier
                    .width(110.dp)
                    .padding(horizontal = 4.dp, vertical = 4.dp)
            }

            Text(
                text = it,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.light,
                modifier = modifier,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TabSwitcherPreview() {
    val tabs = listOf("All", "Important")

    WriterMeTheme {
        TabSwitcher(tabs = tabs, tabs[0], ) {}
    }
}