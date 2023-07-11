package io.writerme.app.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.writerme.app.utils.push
import io.writerme.app.utils.realmListOf
import java.util.Date

open class Note: RealmObject() {
    @Index
    @PrimaryKey
    var id: Long = System.currentTimeMillis()

    var title: String = ""

    var cover: History? = null

    var content: RealmList<History> = realmListOf()

    var created: Date = Date()

    var changeTime: Long = System.currentTimeMillis()

    var tags: RealmList<String> = realmListOf()

    fun setCover(component: Component): Component? {
        if (cover == null) cover = History()

        changeTime = component.changeTime

        return cover!!.push(component, ComponentType.Media)
    }
}