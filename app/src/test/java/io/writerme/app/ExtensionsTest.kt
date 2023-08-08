package io.writerme.app

import io.writerme.app.data.model.Component
import io.writerme.app.data.model.ComponentType
import io.writerme.app.data.model.History
import org.junit.Test
import org.junit.Assert.*

class ExtensionsTest {

    @Test
    fun test_history() {
        val history = History()
        val component = Component().apply {
            title = "test"
            type = ComponentType.Text
        }
        history.push(component)
        val last = history.newest()

        assertNotNull(last)
    }

    @Test
    fun testComponentType() {
        val component = Component()
        component.type = ComponentType.Checkbox

        assertEquals(ComponentType.Checkbox, component.type)
    }
}