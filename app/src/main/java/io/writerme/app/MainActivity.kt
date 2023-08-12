package io.writerme.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import io.writerme.app.utils.Const
import kotlinx.coroutines.flow.MutableStateFlow


class MainActivity : AppCompatActivity() {

    private fun onTermsClicked() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Const.TERMS_LINK))
        startActivity(browserIntent)
    }

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
                        fullName = "Florian Hermes",
                        email = "florian.hermes@email.com",
                        profilePictureUrl = "",
                        currentLanguage = "English",
                        languages = listOf("English", "Deutsch", "Українська"),
                    )

                    SettingsScreen(
                        MutableStateFlow(state),
                        {}, {}, {}, { _, _ ->}
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WriterMeTheme {
        val component = Component().apply {
            type = ComponentType.Checkbox
            content = "Complete writing post for Instagram"
            isChecked = true
        }

        val modifier = Modifier.padding(dimensionResource(id = R.dimen.screen_padding))

        WriterMeTheme {
            Checkbox(component = component, modifier = modifier)
        }
    }
}