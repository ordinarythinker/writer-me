package io.writerme.app.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.data.model.BookmarksFolder
import io.writerme.app.ui.theme.light

@Composable
fun FolderPickerRow(folder: BookmarksFolder, modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(0.dp, 8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_folder),
            contentDescription = stringResource(id = R.string.folder_icon),
            modifier = Modifier.size(30.dp),
            tint = MaterialTheme.colors.light
        )
        Text(
            text = folder.name,
            color = MaterialTheme.colors.light,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(24.dp, 0.dp, 8.dp, 0.dp),
        )
    }
}


