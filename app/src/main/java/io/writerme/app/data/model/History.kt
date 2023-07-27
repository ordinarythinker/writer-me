package io.writerme.app.data.model


import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import io.writerme.app.utils.push

open class History: RealmObject {
    @Index
    @PrimaryKey
    var id: Long = System.currentTimeMillis()
    var changes: RealmList<Component> = realmListOf()

    fun push(component: Component, type: ComponentType): Component? {
        return changes.push(component, type)
    }
}