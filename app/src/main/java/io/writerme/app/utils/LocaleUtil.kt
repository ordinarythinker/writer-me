package io.writerme.app.utils

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

object LocaleUtils {
    fun setLocale(language: String) {
        val tag = languageToTag(language)

        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
    }
    private fun languageToTag(language: String): String {
        return if (Const.SUPPORTED_LANGUAGES.contains(language)) {
            when (language) {
                "Українська" -> "uk"
                "Deutsch" -> "de"
                "Español" -> "es"
                "Française" -> "fr"
                "Русский" -> "ru"
                else -> "en"
            }
        } else "en"
    }
}