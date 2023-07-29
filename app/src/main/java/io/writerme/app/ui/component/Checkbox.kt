package io.writerme.app.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.ui.theme.WriterMeTheme

@Composable
fun Checkbox(component: Component, modifier: Modifier) {
    if (component.type == ComponentType.Checkbox) {
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Icon(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp).padding(0.dp, 3.dp, 0.dp, 0.dp),
                painter = if (component.isChecked) painterResource(id = R.drawable.ic_checked)
                else painterResource(id = R.drawable.ic_unchecked),
                contentDescription = stringResource(id = R.string.checkbox_name)
            )
            Text(
                text = component.content,
                modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp),
                style = MaterialTheme.typography.body1
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
        Checkbox(component = component, modifier = modifier)
    }
}
