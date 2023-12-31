package io.writerme.app.utils

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.RealmList
import io.writerme.app.R
import io.writerme.app.data.model.BookmarksFolder
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.model.History
import io.writerme.app.data.model.Note
import io.writerme.app.data.model.Settings
import io.writerme.app.data.work.ImageLoadingWorker
import io.writerme.app.ui.component.HomeFilterTab
import io.writerme.app.ui.theme.fieldDark
import io.writerme.app.ui.theme.strokeLight
import java.io.File
import java.io.FileOutputStream
import java.util.Locale

fun <T> RealmList<T>.getLast(): T? {
    return if (this.size > 0) {
        if (this.size > 1) {
            this[size-1]
        } else this[0]
    } else null
}

fun Long.toTime(): String {
    var result = ""
    var quotient = this
    var remainder: Long

    val seconds = 60
    val minutes = seconds
    val hours = 24
    val days = 365

    var i = 1

    do {
        val divider = when (i) {
            1 -> seconds
            2 -> minutes
            3 -> hours
            else -> days
        }

        remainder = quotient % divider
        quotient /= divider

        result = if (i > 1) {
            "$remainder:$result"
        } else {
            val rem = if (remainder < 10) {
                "0$remainder"
            } else remainder.toString()

            if (quotient > 0) {
                rem
            } else "0:$rem"
        }

        i++
    } while (quotient > 0)

    return result
}

/**
 * IMPORTANT: objects are not deleted from Realm but should be
 */
fun RealmList<Component>.push(t: Component): Component? {
    val number = when (t.type) {
        ComponentType.Text -> Const.TEXT_CHANGES_HISTORY
        ComponentType.Checkbox -> Const.TEXT_CHANGES_HISTORY
        ComponentType.Link -> Const.LINK_CHANGES_HISTORY
        ComponentType.Image, ComponentType.Video -> Const.MEDIA_CHANGES_HISTORY
        ComponentType.Voice -> Const.VOICE_CHANGES_HISTORY
        ComponentType.Task -> Const.TASK_CHANGES_HISTORY
    }

    val deleted = if (this.size >= number) {
        val element = this[0]
        this.removeAt(0)
        element
    } else null

    this.add(t)

    return deleted
}

fun Context.getCurrentLocale(): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.resources.configuration.locales[0]
    } else {
        this.resources.configuration.locale
    }
}

fun RealmConfiguration.Companion.default(): RealmConfiguration {
    return RealmConfiguration.Builder(
        schema = setOf(BookmarksFolder::class, Component::class, History::class, Note::class, Settings::class)
    )
        .name(Const.DB_NAME)
        .schemaVersion(Const.DB_SCHEMA_VERSION)
        .build()
}

fun Realm.Companion.getDefaultInstance(): Realm {
    return open(RealmConfiguration.default())
}

fun ClipboardManager.copyComponentContent(component: Component, context: Context) {
    val text = when (component.type) {
        ComponentType.Text, ComponentType.Checkbox, ComponentType.Task -> component.content

        ComponentType.Voice, ComponentType.Link,
        ComponentType.Video, ComponentType.Image -> component.url
    }

    this.setText(AnnotatedString(text))

    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
        Toast.makeText(context, context.resources.getString(R.string.copied), Toast.LENGTH_SHORT).show()
}

fun Bitmap.toFile(
    parentFolder: File
) : Uri? {
    return try {
        val file = File("${parentFolder.absolutePath}/image_${System.currentTimeMillis()}.jpg")

        FileOutputStream(file).use { fos ->
            this.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        }

        file.toUri()
    } catch (e: Exception) {
        null
    }
}

fun WorkManager.scheduleImageLoading(componentId: Long, bookmarkId: Long = -1) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()

    val scheduledNetRequest = OneTimeWorkRequestBuilder<ImageLoadingWorker>()
        .setInputData(
            workDataOf(
                ImageLoadingWorker.IMAGE_COMPONENT_ID to componentId,
                ImageLoadingWorker.BOOKMARK_FOLDER_ID to bookmarkId
            )
        )
        .setConstraints(constraints).build()

    this.enqueueUniqueWork(
        "oneFileDownloadWork_${System.currentTimeMillis()}",
        ExistingWorkPolicy.KEEP,
        scheduledNetRequest
    )
}

fun String.toFirstName(): String {
    return if (this.isNotEmpty()) {
        val array = this.split("[\\W\\s]+".toRegex()).toTypedArray()
        return array[0]
    } else this
}

@Composable
fun HomeFilterTab.displayName() : String {
    return when (this) {
        HomeFilterTab.All -> {
            stringResource(id = R.string.all)
        }
        HomeFilterTab.Important -> {
            stringResource(id = R.string.important)
        }
    }
}

@Composable
fun Modifier.textFieldBackground() =
    this
        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.big_radius)))
        .border(
            dimensionResource(id = R.dimen.field_border_width),
            MaterialTheme.colors.strokeLight,
            RoundedCornerShape(dimensionResource(id = R.dimen.big_radius))
        )
        .background(MaterialTheme.colors.fieldDark)
        .padding(dimensionResource(id = R.dimen.screen_padding), 0.dp)
        .height(40.dp)

fun MutableRealm.deleteComponent(component: Component) {
    when (component.type) {
        ComponentType.Voice,
        ComponentType.Link,
        ComponentType.Video, ComponentType.Image -> {
            component.mediaUrl?.let { url ->
                if (url.isNotEmpty()) {
                    try {
                        val file = File(url)

                        if (file.exists()) file.delete()
                    } catch (e: Exception) {
                        Log.e("deleteComponent", "Component deletion is failed", e)
                    }
                }
            }
        }
        else -> {}
    }
    delete(component)
}

fun MutableRealm.deleteHistory(h: History?) {
    h?.let { history ->
        if (history.changes.isNotEmpty()) {
            while (history.changes.isNotEmpty()) {
                val component = history.changes[0]
                deleteComponent(component)
            }
        }
        delete(history)
    }
}

fun MutableRealm.deleteNote(note: Note) {
    deleteHistory(note.title)
    deleteHistory(note.cover)

    if (note.content.isNotEmpty()) {
        while (note.content.isNotEmpty()) {
            val component = note.content[0]
            deleteHistory(component)
        }
    }

    delete(note)
}

fun MutableRealm.deleteBookmarkFolder(folder: BookmarksFolder) {
    while (folder.folders.isNotEmpty()) {
        this.deleteBookmarkFolder(folder.folders[0])
    }

    while (folder.bookmarks.isNotEmpty()) {
        this.deleteComponent(folder.bookmarks[0])
    }

    delete(folder)
}

@Composable
fun checkAndRequestPermission(permission: String, onSuccess: () -> Unit, onNotGrantedMessage: Int) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onSuccess()
        } else {
            Toast.makeText(context, onNotGrantedMessage, Toast.LENGTH_LONG).show()
        }
    }

    val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        onSuccess()
    } else {
        launcher.launch(permission)
    }
}