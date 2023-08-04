package io.writerme.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import io.writerme.app.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun toDateDescription(date: Date): String {
    val now = Calendar.getInstance()
    val given = Calendar.getInstance()
    given.time = date

    val yesterday = Calendar.getInstance()
    yesterday.add(Calendar.DAY_OF_YEAR, -1)

    val tomorrow = Calendar.getInstance()
    tomorrow.add(Calendar.DAY_OF_YEAR, 1)

    val currentLocale = LocalContext.current.getCurrentLocale()

    var _when = if (given.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
        given.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)
    ) {
        stringResource(id = R.string.today)
    } else if (given.get(Calendar.YEAR) == yesterday[Calendar.YEAR] &&
        given.get(Calendar.DAY_OF_YEAR) == yesterday[Calendar.DAY_OF_YEAR]
    ) {
        stringResource(id = R.string.yesterday)
    } else if (given.get(Calendar.YEAR) == tomorrow[Calendar.YEAR] &&
        given.get(Calendar.DAY_OF_YEAR) == tomorrow[Calendar.DAY_OF_YEAR]
    ) {
        stringResource(id = R.string.tomorrow)
    } else {
        val format = SimpleDateFormat("EEEE, d MMMM yy", currentLocale)
        format.format(date)
    }

    val at = stringResource(id = R.string.at)

    val format = SimpleDateFormat("hh:mm a", currentLocale)
    val time = format.format(date)

    return "$_when $at $time"
}