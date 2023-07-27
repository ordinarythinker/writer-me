package io.writerme.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.component.Checkbox
import io.writerme.app.ui.screen.SettingsScreen
import io.writerme.app.ui.state.SettingsState
import io.writerme.app.ui.theme.WriterMeTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Initialize settings

        setContent {
            WriterMeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val state = SettingsState(
                        currentLanguage = "English",
                        languages = listOf("English", "Deutsch", "Українська"),
                        onLanguageChange = {},
                        onDarkModeChange = {},
                        onTermsClick = {},
                        onCounterChange = { _, _ ->}
                    )
                    state.apply {
                        fullName = "Florian Hermes"
                        email = "florian.hermes@email.com"
                    }

                    SettingsScreen(MutableStateFlow(state))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WriterMeTheme {
        val component = Component().apply {
            type = ComponentType.CheckBox
            content = "Complete writing post for Instagram"
            isChecked = true
        }

        val modifier = Modifier.padding(dimensionResource(id = R.dimen.screen_padding))

        WriterMeTheme {
            Checkbox(component = component, modifier = modifier)
        }
    }
}