package io.writerme.app.utils

object Const {

    var MEDIA_CHANGES_HISTORY = 3
    var VOICE_CHANGES_HISTORY = 2
    var TEXT_CHANGES_HISTORY = 5
    var TASK_CHANGES_HISTORY = 3
    var LINK_CHANGES_HISTORY = 3

    const val DB_SCHEMA_VERSION: Long = 0

    const val MEDIA_CHANGES_HISTORY_KEY = "media_changes"
    const val VOICE_CHANGES_HISTORY_KEY = "voice_changes"
    const val TEXT_CHANGES_HISTORY_KEY = "text_changes"
    const val TASK_CHANGES_HISTORY_KEY = "task_changes"
    const val LINK_CHANGES_HISTORY_KEY = "link_changes"

    const val DB_NAME: String = "writer.db"
}