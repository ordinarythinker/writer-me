package io.writerme.app.utils

import android.content.Context
import android.os.Build
import io.realm.RealmList
import io.realm.RealmObject
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import java.util.Locale

fun <T> RealmList<T>.getLast(): T? {
    return if (this.size > 0) {
        if (this.size > 1) {
            this[size-1]
        } else this[0]
    } else null
}

fun <T: RealmObject> realmsOf(vararg values: T): RealmList<T> {
    return RealmList<T>(*values)
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

