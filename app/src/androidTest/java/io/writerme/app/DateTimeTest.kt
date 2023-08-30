package io.writerme.app

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.writerme.app.utils.toDateDescription
import io.writerme.app.utils.toGreeting
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class DateTimeTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_correct_display() {
        val calendar = Calendar.getInstance()
        calendar.set(2023, 7, 31, 20, 11)

        composeTestRule.setContent {
            LocalContext.current.resources.configuration.setLocale(Locale.ENGLISH)

            val text = calendar.time.toDateDescription()

            Assert.assertEquals("Tomorrow at 08:11 PM", text)
        }
    }

    @Test
    fun test_time_depending_greeting_morning() {
        val calendar = Calendar.getInstance()
        calendar.set(2023, 7, 5, 5, 11)

        composeTestRule.setContent {
            val text = calendar.time.toGreeting()
            Assert.assertEquals(stringResource(id = R.string.good_morning), text)
        }
    }

    @Test
    fun test_time_depending_greeting_afternoon() {
        val calendar = Calendar.getInstance()
        calendar.set(2023, 7, 5, 14, 11)

        composeTestRule.setContent {
            val text = calendar.time.toGreeting()
            Assert.assertEquals(stringResource(id = R.string.good_afternoon), text)
        }
    }

    @Test
    fun test_time_depending_greeting_evening() {
        val calendar = Calendar.getInstance()
        calendar.set(2023, 7, 5, 20, 11)

        composeTestRule.setContent {
            val text = calendar.time.toGreeting()
            Assert.assertEquals(stringResource(id = R.string.good_evening), text)
        }
    }

}