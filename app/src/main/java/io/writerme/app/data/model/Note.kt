package io.writerme.app.data.model


import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.Date

open class Note: RealmObject {
    @Index
    @PrimaryKey
    var id: Long = System.currentTimeMillis()

    var title: String = ""

    var cover: History? = null

    var content: RealmList<History> = realmListOf()

    private var _created: Long = 0
    var created: Date
        get() = Date(_created)
        set(value) {
            _created = value.time
        }

    var changeTime: Long = System.currentTimeMillis()

    var tags: RealmList<String> = realmListOf()

    fun setCover(component: Component): Component? {
        if (cover == null) cover = History()

        changeTime = component.changeTime

        return cover!!.push(component, ComponentType.Media)
    }
}