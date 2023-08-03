package io.writerme.app.utils

import androidx.compose.runtime.Composable
import java.util.Calendar
import java.util.Date

@Composable
fun toDateDescription(date: Date): String {
    var result = "Today at 2:40 PM"
    val current = Calendar.getInstance()
    val given = Calendar.getInstance()
    given.time = date

//    if (current.get(Calendar.))


    return result
}