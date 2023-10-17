package io.writerme.app.ui.component

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.light

@Composable
fun Checkbox(
    component: Component,
    onValueChange: (Component) -> Unit,
    onCheckedChange: () -> Unit,
    onAddNewCheckbox: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (component.type == ComponentType.Checkbox) {
        var localText by remember {
            mutableStateOf(component.content)
        }

        val requester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            requester.requestFocus()
        }

        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Icon(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .padding(0.dp, 3.dp, 0.dp, 0.dp)
                    .clickable(onClick = onCheckedChange),
                painter = if (component.isChecked) painterResource(id = R.drawable.ic_checked)
                            else painterResource(id = R.drawable.ic_unchecked),
                contentDescription = stringResource(id = R.string.checkbox_name),
                tint = MaterialTheme.colors.light
            )
            BasicTextField(
                value = localText,
                onValueChange = {
                    localText = it
                    onValueChange(component.copy(content = it))
                },
                modifier = Modifier
                    .padding(16.dp, 0.dp, 0.dp, 0.dp)
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 48.dp)
                    .focusRequester(requester)
                    .focusable()
                    .onKeyEvent {
                        when (it.nativeKeyEvent.keyCode) {
                            KeyEvent.KEYCODE_ENTER,
                            KeyEvent.ACTION_DOWN,
                            KeyEvent.KEYCODE_NUMPAD_ENTER -> {
                                onAddNewCheckbox()
                                true
                            }
                            else -> false
                        }
                    },
                textStyle = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.light),
                cursorBrush = SolidColor(MaterialTheme.colors.light)
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CheckboxPreview() {
    val component = Component().apply {
        type = ComponentType.Checkbox
        content = "Complete writing post for Instagram, Complete writing post for Instagram, Complete writing post for Instagram"
        isChecked = false
    }

    val modifier = Modifier.padding(dimensionResource(id = R.dimen.screen_padding))

    WriterMeTheme {
        Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray)) {
            Checkbox(component = component, {}, {}, {}, modifier = modifier)
        }
    }
}
