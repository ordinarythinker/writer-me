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

    companion object {
        fun empty(): SettingsState =SettingsState(
            fullName = "",
            email = "",
            profilePictureUrl = "",
            currentLanguage = ""
        )
    }
}