package io.writerme.app.data.audio

interface MediaListener {
    fun scrollTo(time: Long)
    fun pause()
    fun resume()
    fun isPaused(): Boolean
}