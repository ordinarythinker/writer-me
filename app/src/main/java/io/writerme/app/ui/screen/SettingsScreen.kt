package io.writerme.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme

@Composable
fun SettingsScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(scrollState, Orientation.Vertical)
            .paint(painterResource(id = R.drawable.background_main), contentScale = ContentScale.Crop)
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    WriterMeTheme {
        SettingsScreen()
    }
}
