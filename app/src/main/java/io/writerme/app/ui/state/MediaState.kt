package io.writerme.app.ui.state

import io.writerme.app.data.model.Component

abstract class MediaState(val media: Component) {
    abstract fun scrollTo(time: Long)
    abstract fun pause()
    abstract fun resume()

    abstract fun isPaused(): Boolean
}