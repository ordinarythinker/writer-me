package io.writerme.app.ui.state

class SettingsState(
    val languages: List<String>,
    val onTermsClick: () -> Unit,
    val onLanguageChange: (String) -> Unit,
    val onDarkModeChange: (Boolean) -> Unit
) {
    var isDarkMode: Boolean = false
    val profilePictureUrl = ""
    var fullName = ""
    var email = ""
}