package io.writerme.app

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.writerme.app.utils.toDateDescription
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class DateTimeUtilTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_correct_display() {
        val calendar = Calendar.getInstance()
        calendar.set(2023, 7, 5, 20, 11)

        composeTestRule.setContent {
            LocalContext.current.resources.configuration.setLocale(Locale.ENGLISH)

            val text = toDateDescription(date = calendar.time)

            Assert.assertEquals("Tomorrow at 08:11 PM", text)
        }
    }
}