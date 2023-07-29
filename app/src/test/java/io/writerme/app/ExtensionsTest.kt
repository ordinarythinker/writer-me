package io.writerme.app

import io.realm.kotlin.ext.realmListOf
import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.utils.push
import org.junit.Test
import org.junit.Assert.*

class ExtensionsTest {

    @Test
    fun testPush_Empty() {
        val list = realmListOf<Int>()
        list.push(1, ComponentType.Text)
        assertEquals(1, list.size)
    }

    @Test
    fun testPush_TextType() {
        val list = realmListOf(1,2,3,4,5)
        list.push(6, ComponentType.Text)
        assertEquals(5, list.size)
    }

    @Test
    fun testPush_VoiceType() {
        val list = realmListOf(1,2,3,4,5)
        list.push(6, ComponentType.Voice)
        assertEquals(2, list.size)
    }

    @Test
    fun testPush_MediaType() {
        val list = realmListOf(1,2,3,4,5)
        list.push(6, ComponentType.Image)
        assertEquals(3, list.size)
    }

    @Test
    fun testPush_TaskType() {
        val list = realmListOf(1,2,3,4,5)
        list.push(6, ComponentType.Task)
        assertEquals(3, list.size)
    }

    @Test
    fun testPush_LinkType() {
        val list = realmListOf(1,2,3,4,5)
        list.push(6, ComponentType.Link)
        assertEquals(3, list.size)
    }

    @Test
    fun testComponentType() {
        val component = Component()
        component.type = ComponentType.Checkbox

        assertEquals(ComponentType.Checkbox, component.type)
    }
}