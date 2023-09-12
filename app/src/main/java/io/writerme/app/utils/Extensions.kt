package io.writerme.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.core.net.toUri
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
    var remainder = 0L

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
 * TODO: consider revision
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
    t.id = System.currentTimeMillis()

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
            this.compress(Bitmap.CompressFormat.JPEG, 1, fos)
        }

        file.toUri()
    } catch (e: Exception) {
        null
    }
}