package io.writerme.app.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.writerme.app.R

val Ubuntu = FontFamily(
    Font(R.font.ubuntu_regular),
    Font(R.font.ubuntu_bold, weight = FontWeight.Bold),
    Font(R.font.ubuntu_light, weight = FontWeight.Light),
    Font(R.font.ubuntu_medium, weight = FontWeight.Medium),
    Font(R.font.ubuntu_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.ubuntu_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.ubuntu_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.ubuntu_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic)
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = Ubuntu,
    h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 23.sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 21.sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)