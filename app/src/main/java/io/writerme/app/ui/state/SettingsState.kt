package io.writerme.app.ui.state

import io.writerme.app.utils.Const

class SettingsState(
    val fullName: String,
    val email: String,
    val profilePictureUrl: String,
    val currentLanguage: String,
    var languages: List<String> = listOf(),
    val isDarkMode: Boolean = true,
    var mediaChanges: Int = Const.MEDIA_CHANGES_HISTORY,
    var voiceChanges: Int = Const.VOICE_CHANGES_HISTORY,
    var textChanges: Int = Const.TEXT_CHANGES_HISTORY,
    var taskChanges: Int = Const.TASK_CHANGES_HISTORY,
    var linkChanges: Int = Const.LINK_CHANGES_HISTORY
) {

    fun copy(
        fullName: String = this.fullName,
        email: String = this.email,
        profilePictureUrl: String = this.profilePictureUrl,
        currentLanguage: String = this.currentLanguage,
        languages: List<String> = this.languages,
        isDarkMode: Boolean = this.isDarkMode,
        mediaChanges: Int = this.mediaChanges,
        voiceChanges: Int = this.voiceChanges,
        textChanges: Int = this.textChanges,
        taskChanges: Int = this.taskChanges,
        linkChanges: Int = this.linkChanges
    ) : SettingsState {
        return SettingsState(
            fullName = fullName,
            email = email,
            profilePictureUrl = profilePictureUrl,
            currentLanguage = currentLanguage,
            languages = languages,
            isDarkMode = isDarkMode,
            mediaChanges = mediaChanges,
            voiceChanges = voiceChanges,
            textChanges = textChanges,
            taskChanges = taskChanges,
            linkChanges = linkChanges
        )
    }


    companion object {
        fun empty(): SettingsState =SettingsState(
            fullName = "",
            email = "",
            profilePictureUrl = "",
            currentLanguage = ""
        )
    }
}