package io.writerme.app.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.data.model.BookmarksFolder
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.ui.theme.light

@Composable
fun Folder(folder: BookmarksFolder, modifier: Modifier = Modifier) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.ic_folder),
            contentDescription = stringResource(id = R.string.folder_icon)
        )

        Text(
            text = folder.name,
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            modifier = Modifier.width(100.dp).padding(0.dp, 8.dp),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.light
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FolderPreview() {
    val folder = BookmarksFolder().apply {
        name = "Long"
    }

    val modifier = Modifier.padding(16.dp)

    WriterMeTheme {
        Folder(folder = folder, modifier = modifier)
    }
}