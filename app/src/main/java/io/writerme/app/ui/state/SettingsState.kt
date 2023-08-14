package io.writerme.app.ui.state

class SettingsState(
    val fullName: String,
    val email: String,
    val profilePictureUrl: String,
    val currentLanguage: String,
    var languages: List<String> = listOf(),
    val isDarkMode: Boolean = true,
    var mediaChanges: Int = 0,
    var voiceChanges: Int = 0,
    var textChanges: Int = 0,
    var taskChanges: Int = 0,
    var linkChanges: Int = 0
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