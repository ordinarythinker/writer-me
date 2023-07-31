package io.writerme.app.data.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey

open class BookmarksFolder : RealmObject {
    @Index
    @PrimaryKey
    var id: Long = System.currentTimeMillis()
    var name: String = ""
    var folders: RealmList<BookmarksFolder> = realmListOf()
    var bookmarks: RealmList<Component> = realmListOf()


}