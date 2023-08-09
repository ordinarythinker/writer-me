package io.writerme.app.data.model

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.writerme.app.utils.Const

open class Settings: RealmObject() {
    @Index
    @PrimaryKey
    var id = 0

    var fullName: String = ""

    var email: String = ""

    var mediaChanges: Int = Const.MEDIA_CHANGES_HISTORY
    var voiceChanges: Int = Const.VOICE_CHANGES_HISTORY
    var textChanges: Int = Const.TEXT_CHANGES_HISTORY
    var taskChanges: Int = Const.TASK_CHANGES_HISTORY
    var linkChanges: Int = Const.LINK_CHANGES_HISTORY

    fun setHistory(key: String, value: Int) {
        when (key) {
            Const.MEDIA_CHANGES_HISTORY_KEY -> this.mediaChanges = value
            Const.VOICE_CHANGES_HISTORY_KEY -> this.voiceChanges = value
            Const.TEXT_CHANGES_HISTORY_KEY -> this.textChanges = value
            Const.TASK_CHANGES_HISTORY_KEY -> this.taskChanges = value
            Const.LINK_CHANGES_HISTORY_KEY -> this.linkChanges = value
        }
    }
}