package io.writerme.app.utils

import io.realm.kotlin.types.RealmList
import io.writerme.app.data.model.ComponentType

fun <T> RealmList<T>.getLast(): T? {
    return if (this.size > 0) {
        if (this.size > 1) {
            this[size-1]
        } else this[0]
    } else null
}

/**
 * IMPORTANT: objects are not deleted from Realm but should be
 * TODO: consider revision
 */
fun <T> RealmList<T>.push(t: T, type: ComponentType): T? {
    val number = when (type) {
        ComponentType.Text -> Const.TEXT_CHANGES_HISTORY
        ComponentType.CheckBox -> Const.TEXT_CHANGES_HISTORY
        ComponentType.Link -> Const.LINK_CHANGES_HISTORY
        ComponentType.Media -> Const.MEDIA_CHANGES_HISTORY
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