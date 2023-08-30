package io.writerme.app.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF0A162C)
val Purple700 = Color(0xFF0A162C)
val Teal200 = Color(0xFF4E688D)

// TODO: insert all the required colors

val DarkGrey = Color(0xA6292D32)
val TextLight = Color(0xFFF5F5F5)
val LightGrey50 = Color(0x808C8989)
val LightGrey65 = Color(0xA6D9D9D9)
val DarkGrey65 = Color(0xA66B6A6A)
val DialogGrey85 = Color(0xD9292D32)
val DarkBlack25 = Color(0x40000000)

val ExtraLight15 = Color(0x26D9D9D9)

val Colors.cardBackground : Color
    get() = DarkGrey

val Colors.light : Color
    get() = TextLight

val Colors.strokeLight : Color
    get() = LightGrey50

val Colors.linkTitle : Color
    get() = LightGrey65

val Colors.dialogBackground : Color
    get() = DialogGrey85

val Colors.fieldDark : Color
    get() = DarkBlack25

val Colors.backgroundGrey : Color
    get() = DarkGrey65

val Colors.lightTransparent : Color
    get() = ExtraLight15