package io.writerme.app.ui.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.drawscope.Stroke
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
import io.writerme.app.ui.theme.strokeLight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GreetingScreen(proceed: () -> Unit) {

    val welcome = stringResource(id = R.string.welcome)
    val thankYou = stringResource(id = R.string.thank_you)

    var hasImageAnimationStarted by remember {
        mutableStateOf(false)
    }

    var hasTopArcAnimationStarted by remember {
        mutableStateOf(false)
    }

    var hasBottomArcAnimationStarted by remember {
        mutableStateOf(false)
    }

    var welcomeText by remember {
        mutableStateOf("")
    }

    var thankYouText by remember {
        mutableStateOf("")
    }

    val topAngle: Float by animateFloatAsState(
        targetValue = if (hasTopArcAnimationStarted) -160f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = "topAngle"
    )

    val bottomAngle: Float by animateFloatAsState(
        targetValue = if (hasBottomArcAnimationStarted) 90f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "bottomAngle"
    )

    LaunchedEffect(
        key1 = null,
    ) {
        launch {
            delay(500)
            hasImageAnimationStarted = true

            delay(1500)
            hasTopArcAnimationStarted = true

            delay(700)
            hasBottomArcAnimationStarted = true
        }

        launch {
            delay(2000)

            welcome.forEachIndexed { charIndex, _ ->
                welcomeText = welcome.substring(
                    startIndex = 0,
                    endIndex = charIndex + 1,
                )
                delay(120)
            }

            delay(800)

            thankYou.forEachIndexed { charIndex, _ ->
                thankYouText = thankYou.substring(
                    startIndex = 0,
                    endIndex = charIndex + 1,
                )
                delay(90)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_main),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val shape = RoundedCornerShape(
                topStart = 230.dp, topEnd = 230.dp, bottomEnd = 236.dp, bottomStart = 230.dp
            )

            Box(
                modifier = Modifier
                    .size(height = 400.dp, width = 400.dp)
                    .offset(x = (-80).dp, y = 55.dp)
            ) {
                val color = MaterialTheme.colors.strokeLight

                this@Column.AnimatedVisibility(
                    visible = hasImageAnimationStarted,
                    enter = slideInHorizontally(
                        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
                    ) + scaleIn(
                        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
                    ),
                    exit = slideOutHorizontally() + scaleOut()
                ) {
                    Card(
                        modifier = Modifier
                            .offset(x = 18.dp, y = 20.dp)
                            .wrapContentHeight()
                            .shadow(20.dp, shape),
                        shape = shape
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.greeting),
                            contentDescription = stringResource(id = R.string.image),
                            modifier = Modifier
                                .size(width = 360.dp, height = 360.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    drawArc(
                        color = color,
                        startAngle = 10f,
                        sweepAngle = topAngle,
                        useCenter = false,
                        style = Stroke(width = 2.dp.toPx())
                    )

                    drawArc(
                        color = color,
                        startAngle = 55f,
                        sweepAngle = bottomAngle,
                        useCenter = false,
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
            }

            val startPadding = 34.dp

            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = welcomeText,
                    color = MaterialTheme.colors.light,
                    style = MaterialTheme.typography.h1.copy(fontSize = 40.sp, fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(start = startPadding, top = 120.dp)
                )
            }

            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = thankYouText,
                    color = MaterialTheme.colors.light,
                    style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(start = startPadding, top = 24.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = proceed,
                modifier = Modifier
                    .align(Alignment.End)
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


@Preview
@Composable
fun GreetingScreenPreview() {
    WriterMeTheme {
        GreetingScreen({})
    }
}