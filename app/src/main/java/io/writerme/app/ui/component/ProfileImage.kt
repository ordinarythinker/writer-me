package io.writerme.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.light

@Composable
fun ProfileImage(url: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(66.dp)
            .clip(RoundedCornerShape(30.dp, 7.dp, 30.dp, 30.dp))
            .background(MaterialTheme.colors.light)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        /*Image(
            painter = painterResource(id = R.drawable.florian),
            contentDescription = stringResource(id = R.string.profile_picture),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(53.dp)
                .clip(CircleShape)
        )*/

        AsyncImage(
            model = url,
            contentDescription = stringResource(id = R.string.profile_picture),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(53.dp)
                .clip(CircleShape)
        )
    }
}

@Preview
@Composable
fun ProfileImagePreview() {
    WriterMeTheme {
        ProfileImage(url = "", {})
    }
}