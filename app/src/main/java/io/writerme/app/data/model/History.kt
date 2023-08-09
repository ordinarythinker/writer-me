package io.writerme.app.data.model


import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.writerme.app.utils.getLast
import io.writerme.app.utils.push

open class History: RealmObject {
    @PrimaryKey
    var id: Long = System.currentTimeMillis()

    var changes: RealmList<Component> = RealmList()

    constructor(): super()

    constructor(component: Component): this () {
        changes.add(component)
    }

    fun push(component: Component): Component? {
        return changes.push(component)
    }

    fun newest(): Component? = changes.getLast()

    fun isNotEmpty(): Boolean = changes.isNotEmpty()
}