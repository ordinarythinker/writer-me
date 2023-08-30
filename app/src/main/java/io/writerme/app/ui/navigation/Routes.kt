package io.writerme.app.ui.navigation

interface Route {
    val route: String
}

object Home : Route {
    override val route: String = "home"
}

object Task : Route {
    override val route: String = "task"
}

object Note : Route {
    override val route: String = "note"
}

object Bookmarks : Route {
    override val route: String = "bookmarks"
}

object Settings : Route {
    override val route: String = "settings"
}