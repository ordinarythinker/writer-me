package io.writerme.app.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import io.writerme.app.ui.state.SettingsState
import io.writerme.app.utils.Const

open class Settings: RealmObject {
    @Index
    @PrimaryKey
    var id = 0

    var fullName: String = ""

    var email: String = ""

    var profilePictureUrl: String = ""

    var mediaChanges: Int = Const.MEDIA_CHANGES_HISTORY
    var voiceChanges: Int = Const.VOICE_CHANGES_HISTORY
    var textChanges: Int = Const.TEXT_CHANGES_HISTORY
    var taskChanges: Int = Const.TASK_CHANGES_HISTORY
    var linkChanges: Int = Const.LINK_CHANGES_HISTORY

    var currentLanguage: String = ""

    var isDarkMode: Boolean = true

    fun setHistory(key: String, value: Int) {
        when (key) {
            Const.MEDIA_CHANGES_HISTORY_KEY -> this.mediaChanges = value
            Const.VOICE_CHANGES_HISTORY_KEY -> this.voiceChanges = value
            Const.TEXT_CHANGES_HISTORY_KEY -> this.textChanges = value
            Const.TASK_CHANGES_HISTORY_KEY -> this.taskChanges = value
            Const.LINK_CHANGES_HISTORY_KEY -> this.linkChanges = value
        }
    }

    fun toState(): SettingsState = SettingsState(
        fullName = fullName,
        email = email,
        profilePictureUrl = profilePictureUrl,
        currentLanguage = currentLanguage,
        isDarkMode = isDarkMode,
        mediaChanges = mediaChanges,
        voiceChanges = voiceChanges,
        textChanges = textChanges,
        taskChanges = taskChanges,
        linkChanges = linkChanges
    )
}