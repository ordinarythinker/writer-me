package io.writerme.app.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.light

@Composable
fun RegistrationScreen(
    proceedToNextScreen: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_main),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.weight(1f))


            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally(
                    initialOffsetX = { it/2 },
                    animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                ) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { it/2 }) + fadeOut(),
                modifier = Modifier.align(Alignment.End)
            ) {
                IconButton(
                    onClick = proceedToNextScreen,
                    modifier = Modifier
                        .padding(end = 32.dp, bottom = 32.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = stringResource(id = R.string.proceed),
                        tint = MaterialTheme.colors.light
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    WriterMeTheme {
        RegistrationScreen({})
    }
}