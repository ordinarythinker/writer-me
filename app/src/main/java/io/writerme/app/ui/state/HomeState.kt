package io.writerme.app.ui.state

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.writerme.app.data.model.Note

data class HomeState(
    val firstName: String = "",
    val profilePhotoUrl: String = "",
    val isImportantVisible: String = "",
    val isFoldersVisible: String = "",
    val tabs: List<String> = listOf(),
    val chosenTab: String = "",
    var notes: RealmList<Note> = realmListOf()
)