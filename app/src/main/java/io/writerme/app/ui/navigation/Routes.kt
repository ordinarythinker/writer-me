package io.writerme.app.ui.navigation

interface Route {
    val route: String
}

object HomeScreen : Route {
    override val route: String = "home"
}

object TasksScreen : Route {
    override val route: String = "task"
}

object GreetingScreen : Route {
    override val route: String = "greeting"
}

object RegistrationScreen : Route {
    override val route: String = "registration"
}

object NoteScreen : Route {
    const val NOTE_PARAM = "id"

    override val route: String = "note?id={$NOTE_PARAM}"
    const val navigationRoute: String = "note?id="
}

object BookmarksScreen : Route {
    override val route: String = "bookmarks"
}

object SettingsScreen : Route {
    override val route: String = "settings"
}