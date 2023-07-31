package io.writerme.app.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.writerme.app.R
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.theme.WriterMeTheme

@Composable
fun Image(component: Component, modifier: Modifier) {
    if (component.type == ComponentType.Image) {
        val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))

        Card(
            shape = shape,
            modifier = modifier
                .wrapContentHeight()
                .shadow(dimensionResource(id = R.dimen.shadow), shape),
            backgroundColor = Color.White
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.travel),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            /*AsyncImage(
                model = component.imageUrl,
                contentDescription = component.content,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )*/
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ImagePreview() {
    val imageComponent = Component().apply {
        type = ComponentType.Image
    }

    val modifier = Modifier.padding(dimensionResource(id = R.dimen.screen_padding))

    WriterMeTheme {
        Image(component = imageComponent, modifier = modifier)
    }
}