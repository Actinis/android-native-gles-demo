package io.actinis.opengl.sample.messenger.data

data class Chat(
    val id: Int,
    val name: String,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int,
    val avatarPath: String,
)
