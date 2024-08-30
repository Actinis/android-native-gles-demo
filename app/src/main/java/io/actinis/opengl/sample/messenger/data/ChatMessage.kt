package io.actinis.opengl.sample.messenger.data

import java.util.*

data class ChatMessage(val content: String, val type: Int, val timestamp: Date) {
    companion object {
        const val TYPE_TEXT = 0
        const val TYPE_IMAGE = 1
    }
}
