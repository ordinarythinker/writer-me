package io.writerme.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import io.writerme.app.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun Date.toDateDescription(): String {
    val now = Calendar.getInstance()
    val given = Calendar.getInstance()
    given.time = this

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
        format.format(this)
    }

    val at = stringResource(id = R.string.at)

    val format = SimpleDateFormat("hh:mm a", currentLocale)
    val time = format.format(this)

    return "$_when $at $time"
}

@Composable
fun Date.toGreeting() : String {
    val calendar = Calendar.getInstance()
    calendar.time = this

    return when (calendar.get(Calendar.HOUR_OF_DAY)) {
        in 1..11 -> stringResource(id = R.string.good_morning)
        in 11..17 -> stringResource(id = R.string.good_afternoon)
        else -> stringResource(id = R.string.good_evening)
    }
}