package io.writerme.app.data.model


import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import java.util.Date

open class Note: RealmObject() {
    @Index
    @PrimaryKey
    var id: Long = System.currentTimeMillis()

    var title: History? = null

    var cover: History? = null

    var content: RealmList<History> = RealmList()

    private var _created: Long = 0
    var created: Date
        get() = Date(_created)
        set(value) {
            _created = value.time
        }

    var changeTime: Long = System.currentTimeMillis()

    var tags: RealmList<String> = RealmList()

    fun setCover(imageComponent: Component): Component? {
        changeTime = imageComponent.changeTime

        return cover?.push(imageComponent)
    }
}