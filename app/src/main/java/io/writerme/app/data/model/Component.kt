package io.writerme.app.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.Date

/**
 * Since Realm doesn't provide polymorphism support
 * all the different component types, such as text,
 * voice records, images, videos, tasks, lists, and
 * links are combined in the following model class
 * as an analog to the 1:N polymorphic relation into
 * the single SQL table
 */
open class Component(): RealmObject {
    @Index
    @PrimaryKey
    var id: Long = System.currentTimeMillis()

    var noteId: Long = 0

    var content: String = ""

    var isChecked: Boolean = false

    var url: String = ""

    var title: String = ""

    var imageUrl: String? = null

    private var _time: Long = 0
    var time: Date
        get() = Date(_time)
        set(value) {
            _time = value.time
        }

    var isImage: Boolean = true

    var changeTime: Long = System.currentTimeMillis()

    private var _type: String = ComponentType.Text.value
    var type: ComponentType
        get() {
            return try {
                ComponentType.valueOf(_type)
            } catch (e: IllegalArgumentException) {
                ComponentType.Text
            }
        }
        set(value) {
            _type = value.value
        }

    constructor(note: Note,
                content: String) : this() {
        this.noteId = note.id
        this.content = content
        this.type = ComponentType.Text
    }

    constructor(note: Note,
                content: String,
                isChecked: Boolean) : this() {
        this.noteId = note.id
        this.content = content
        this.type = ComponentType.Checkbox
    }

    constructor(note: Note,
                date: Date,
                description: String) : this() {
        this.noteId = note.id
        this.time = date
        this.content = description
        this.type = ComponentType.Task
    }

    // voice, media (image, video)
    constructor(note: Note,
                url: String,
                type: ComponentType) : this() {
        this.noteId = note.id
        this.url = url
        this.type = type
    }

    constructor(note: Note,
                url: String,
                title: String,
                description: String? = null,
                imageUrl: String? = null) : this() {
        this.noteId = note.id
        this.url = url
        this.title = title
        description?.let { this.content = it }
        this.imageUrl = imageUrl
        this.type = ComponentType.Link
    }

    fun copy(
        id: Long = System.currentTimeMillis(),
        noteId: Long = this.noteId,
        content: String = this.content,
        isChecked: Boolean = this.isChecked,
        url: String = this.url,
        title: String = this.title,
        imageUrl: String? = this.imageUrl,
        time: Date = this.time,
        isImage: Boolean = this.isImage,
        changeTime: Long = this.changeTime,
        type: ComponentType = this.type
    ): Component {
        return Component().apply {
            this.id = id
            this.noteId = noteId
            this.content = content
            this.isChecked = isChecked
            this.url = url
            this.title = title
            this.imageUrl = imageUrl
            this.time = time
            this.isImage = isImage
            this.changeTime = changeTime
            this.type = type
        }
    }
}

enum class ComponentType(val value: String) {
    Text("Text"), Checkbox("Checkbox"),
    Voice("Voice"), Task("Task"),
    Link("Link"), Video("Video"), Image("Image")
}