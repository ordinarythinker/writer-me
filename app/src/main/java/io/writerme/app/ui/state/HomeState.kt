package io.writerme.app.ui.state

import io.writerme.app.data.model.Note
import io.writerme.app.ui.component.HomeFilterTab

data class HomeState(
    val firstName: String = "",
    val profilePhotoUrl: String = "",
    val chosenTab: HomeFilterTab = HomeFilterTab.All,
    val isSearchMode: Boolean = false,
    var notes: List<Note> = listOf()
)