package io.writerme.app.ui.state

import io.writerme.app.data.model.Component

class AudioState(audio: Component): MediaState(audio) {
    override fun scrollTo(time: Long) {
        // TODO
    }

    override fun pause() {
        //TODO
    }

    override fun resume() {
        //TODO
    }

    override fun isPaused(): Boolean {
        return true
    }
}