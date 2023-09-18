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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.light
import io.writerme.app.utils.textFieldBackground
import kotlinx.coroutines.delay

@Composable
fun RegistrationScreen(
    proceedToNextScreen: () -> Unit
) {
    val welcome = stringResource(id = R.string.welcome)

    var welcomeText by remember {
        mutableStateOf("")
    }

    var name by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = null) {
        welcome.forEachIndexed { charIndex, _ ->
            welcomeText = welcome.substring(
                startIndex = 0,
                endIndex = charIndex + 1,
            )
            delay(120)
        }
    }

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
                .padding(34.dp)
        ) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = welcome,
                    color = MaterialTheme.colors.light,
                    style = MaterialTheme.typography.h1.copy(fontSize = 40.sp, fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(top = 100.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            BasicTextField(
                value = name,
                maxLines = 1,
                onValueChange = { name = it },
                modifier = Modifier
                    .textFieldBackground()
                    .fillMaxWidth(),
                singleLine = true,
                textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.light),
                decorationBox = { innerTextField ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (name.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.full_name),
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.light
                            )
                        }
                        innerTextField()
                    }
                },
            )

            Spacer(modifier = Modifier.height(40.dp))

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
                    onClick = proceedToNextScreen
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