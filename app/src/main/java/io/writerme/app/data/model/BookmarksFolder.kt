package io.writerme.app.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey


open class BookmarksFolder : RealmObject() {
    @Index
    @PrimaryKey
    var id: Long = System.currentTimeMillis()
    var name: String = ""

    var folders: RealmList<BookmarksFolder> = RealmList()
    var bookmarks: RealmList<Component> = RealmList()

    var parent: BookmarksFolder? = null

    @Ignore
    val hasParentFolder: Boolean = parent != null

    @delegate:Ignore
    val path: String by lazy {
        var fullPath = ""
        var folder: BookmarksFolder? = this
        do {
            if (folder!!.name.isNotEmpty()) {
                fullPath = "/${folder.name}$fullPath"
            }
            folder = folder.parent
        } while (folder != null && folder.hasParentFolder)

        fullPath
    }

}