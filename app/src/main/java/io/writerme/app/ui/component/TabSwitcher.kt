package io.writerme.app.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
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
    val tabWidth = dimensionResource(id = R.dimen.tab_width)
    val radius = dimensionResource(id = R.dimen.small_radius)
    val shape = RoundedCornerShape(radius)

    val offset by animateDpAsState(
        targetValue = try {
            val index = tabs.indexOf(chosen)
            tabWidth * index
        } catch (e: Exception) {
            0.dp
        },
        animationSpec = tween(durationMillis = 300), label = ""
    )

    Layout(
        modifier = Modifier
            .background(MaterialTheme.colors.lightTransparent, shape)
            .border(1.dp, MaterialTheme.colors.strokeLight, shape)
            .padding(vertical = 4.dp),
        content = {
            // Slider Box
            Box(
                modifier = Modifier
                    .width(tabWidth)
                    .offset(x = offset)
                    .padding(horizontal = 4.dp)
                    .background(MaterialTheme.colors.lightTransparent, shape)
                    .border(1.dp, MaterialTheme.colors.strokeLight, shape)
                    .padding(vertical = 4.dp)
            )

            // Tabs Row
            Row {
                tabs.forEach {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.light,
                        modifier = Modifier
                            .width(tabWidth)
                            .padding(4.dp)
                            .clickable {
                                if (it != chosen) onItemChosen(it)
                            },
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    ) { measurables, constraints ->
        // Measure the Row
        val rowMeasurable = measurables[1]
        val rowPlaceable = rowMeasurable.measure(constraints)

        // Use the height of the Row to constrain the height of the Slider Box
        val boxMeasurable = measurables[0]
        val boxPlaceable = boxMeasurable.measure(constraints.copy(minHeight = rowPlaceable.height))

        // Determine the width and height of the Layout
        val width = maxOf(boxPlaceable.width, rowPlaceable.width)
        val height = rowPlaceable.height

        layout(width, height) {
            boxPlaceable.place(0, 0, 0F)
            rowPlaceable.place(0, 0, 1F)
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