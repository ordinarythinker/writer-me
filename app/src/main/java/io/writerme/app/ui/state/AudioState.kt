package io.writerme.app.ui.state

import io.writerme.app.data.model.Component

data class AudioState(
    val audio: Component,
    val currentProgress: Long,
    val entireLength: Long
)