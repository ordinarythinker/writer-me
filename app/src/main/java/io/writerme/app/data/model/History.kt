package io.writerme.app.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.writerme.app.utils.push

open class History(
    @Index
    @PrimaryKey
    var id: Long = System.currentTimeMillis(),
    var changes: RealmList<Component> = RealmList()
) : RealmObject() {

    fun push(component: Component, type: ComponentType): Component? {
        return changes.push(component, type)
    }
}