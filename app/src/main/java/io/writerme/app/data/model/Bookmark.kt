package io.writerme.app.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey

open class Bookmark : RealmObject {

    @Index
    @PrimaryKey
    var id : Long  = System.currentTimeMillis()

    var name: String = ""
    var url: String = ""

}