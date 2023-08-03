package io.writerme.app.utils

import androidx.compose.runtime.Composable
import java.util.Calendar
import java.util.Date

@Composable
fun toDateDescription(date: Date): String {
    var result = "Today at 2:40 PM"
    val now = Calendar.getInstance()
    val given = Calendar.getInstance()
    given.time = date

    val yesterday = Calendar.getInstance()
    yesterday.add(Calendar.DAY_OF_YEAR, -1)

    val tomorrow = Calendar.getInstance()
    tomorrow.add(Calendar.DAY_OF_YEAR, 1)

    if (given.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
        given.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)
    ) {
        // today
    } else if (given.get(Calendar.YEAR) == yesterday[Calendar.YEAR] &&
        given.get(Calendar.DAY_OF_YEAR) == yesterday[Calendar.DAY_OF_YEAR]
    ) {
        println("The date was yesterday!")
    } else if (given.get(Calendar.YEAR) == tomorrow[Calendar.YEAR] &&
        given.get(Calendar.DAY_OF_YEAR) == tomorrow[Calendar.DAY_OF_YEAR]
    ) {
        println("The date is tomorrow!")
    } else {
        println("The date is not yesterday or tomorrow!")
    }


    return result
}