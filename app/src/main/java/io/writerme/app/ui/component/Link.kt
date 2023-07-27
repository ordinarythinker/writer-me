package io.writerme.app.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.writerme.app.R
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.linkTitle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Link(link: Component, modifier: Modifier, onClick: () -> Unit) {
    val shape = RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))
    Card(
        shape = shape,
        modifier = modifier.wrapContentHeight().shadow(15.dp, shape),
        backgroundColor = Color.White,
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            /*Image(
                painter = painterResource(id = R.drawable.travel),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )*/

            AsyncImage(
               model = link.imageUrl,
               contentDescription = link.content,
               modifier = Modifier
                   .fillMaxWidth(),
               contentScale = ContentScale.Crop
           )

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .align(Alignment.BottomStart)
                .padding(12.dp, 0.dp, 12.dp, 12.dp)
                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.big_radius)))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.linkTitle)
                        .blur(60.dp)
                )

                Text(
                    text = link.title,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterStart)
                        .padding(8.dp, 0.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LinkPreview() {
    val link = Component().apply {
        type = ComponentType.Link
        title = "Top Travel Guide Top Travel Guide Top Travel Guide Top Travel Guide"
        url = "https://writerme.io"
        imageUrl = "file:///android_asset/tropical_1.jpg"
    }

    val modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.screen_padding))

    WriterMeTheme {
        Link(link = link, modifier = modifier) {}
    }
}