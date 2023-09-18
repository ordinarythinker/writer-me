package io.writerme.app.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import io.writerme.app.R
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.dropdownBackground
import io.writerme.app.ui.theme.light
import io.writerme.app.ui.theme.lightGrey
import io.writerme.app.ui.theme.strokeLight
import io.writerme.app.utils.textFieldBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    saveName: (String) -> Unit,
    proceedToNextScreen: () -> Unit,
) {
    val typing = stringResource(id = R.string.typing)

    var name by remember {
        mutableStateOf("")
    }

    var isFirstMessageVisible by remember {
        mutableStateOf(false)
    }

    var isSecondMessageVisible by remember {
        mutableStateOf(false)
    }

    var isUserMessageVisible by remember {
        mutableStateOf(false)
    }

    var isLastMessageVisible by remember {
        mutableStateOf(false)
    }

    var isTyping by remember {
        mutableStateOf(true)
    }

    var typingText by remember {
        mutableStateOf(typing)
    }

    LaunchedEffect(key1 = null) {
        launch {
            var millis = 0
            var counter = 1
            while (counter <= 3) {
                typingText = when(counter) {
                    1 -> "$typing."
                    2 -> "$typing.."
                    3 -> {
                        counter = 1
                        "$typing..."
                    }
                    else -> typing
                }
                counter++
                delay(100)

                millis += 100

                if (millis == 500) isFirstMessageVisible = true
                if (millis == 3000) break
            }
            isTyping = false
            isSecondMessageVisible = true
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
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_writer_me),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier.size(50.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.light
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            //isFirstMessageVisible
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally() + fadeIn(),
                exit = slideOutHorizontally() + fadeOut()
            ) {
                Text(
                    text = stringResource(id = R.string.hi),
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Normal),
                    color = MaterialTheme.colors.light.copy(alpha = 0.7f),
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .background(
                            color = MaterialTheme.colors.dropdownBackground,
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomEnd = 0.dp,
                                bottomStart = 0.dp
                            )
                        )
                        .padding(12.dp)

                )
            }

            //isSecondMessageVisible
            if (true) {
                Spacer(modifier = Modifier.height(2.dp))
            }

            // isSecondMessageVisible
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally() + fadeIn(),
                exit = slideOutHorizontally() + fadeOut()
            ) {
                Text(
                    text = stringResource(id = R.string.introduce),
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Normal),
                    color = MaterialTheme.colors.light.copy(alpha = 0.7f),
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .background(
                            color = MaterialTheme.colors.dropdownBackground,
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomEnd = 8.dp,
                                bottomStart = 0.dp
                            )
                        )
                        .padding(12.dp)

                )
            }

            // isUserMessageVisible
            if (true) {
                Spacer(modifier = Modifier.height(6.dp))
            }

            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally(initialOffsetX = { it/2 }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { it/2 }) + fadeOut(),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Florian Hermes",
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Normal),
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .background(
                            color = MaterialTheme.colors.strokeLight,
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomEnd = 0.dp,
                                bottomStart = 8.dp
                            )
                        )
                        .padding(12.dp)

                )
            }

            // isLastMessageVisible
            if (true) {
                Spacer(modifier = Modifier.height(6.dp))
            }

            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally() + fadeIn(),
                exit = slideOutHorizontally() + fadeOut()
            ) {
                Text(
                    text = stringResource(id = R.string.saving),
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Normal),
                    color = MaterialTheme.colors.light.copy(alpha = 0.7f),
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .background(
                            color = MaterialTheme.colors.dropdownBackground,
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomEnd = 8.dp,
                                bottomStart = 0.dp
                            )
                        )
                        .padding(12.dp)

                )
            }

            if (isTyping) {
                Spacer(modifier = Modifier.height(16.dp))
            }

            AnimatedVisibility(
                visible = isTyping,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = typingText,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.lightGrey
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = name,
                    maxLines = 1,
                    onValueChange = {
                        name = it
                    },
                    modifier = Modifier
                        .textFieldBackground()
                        .weight(1f),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.light),
                    decorationBox = { innerTextField ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (name.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.full_name),
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.lightGrey
                                )
                            }
                            innerTextField()
                        }
                    },
                )

                IconButton(
                    onClick = {
                        /*TODO*/
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = stringResource(id = R.string.send_button),
                        tint = MaterialTheme.colors.lightGrey,
                        modifier = Modifier.size(30.dp)
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
        RegistrationScreen({}, {})
    }
}