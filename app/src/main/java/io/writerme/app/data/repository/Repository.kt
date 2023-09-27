package io.writerme.app.data.repository

abstract class Repository {
    private var currentId = System.currentTimeMillis()

    fun nextId(): Long {
        return currentId++
    }
}