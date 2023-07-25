package io.writerme.app.ui.state

class SettingsState(
    val currentLanguage: String,
    val languages: List<String>,
    val onLanguageChange: (String) -> Unit,
    val onDarkModeChange: (Boolean) -> Unit,
    val onTermsClick: () -> Unit,
    val onCounterChange: (Int, String) -> Unit,
) {
    var isDarkMode: Boolean = true
    val profilePictureUrl = ""
    var fullName = ""
    var email = ""
}