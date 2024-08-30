package io.actinis.opengl.sample.messenger.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.actinis.opengl.sample.R
import io.actinis.opengl.sample.messenger.adapter.ChatAdapter
import io.actinis.opengl.sample.messenger.data.ChatMessage
import java.util.*
import kotlin.random.Random

class ChatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button
    private val messages = mutableListOf<ChatMessage>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        messageInput = view.findViewById(R.id.messageInput)
        sendButton = view.findViewById(R.id.sendButton)

        setupRecyclerView()
        loadMessages()
        setupSendButton()

        return view
    }

    private fun setupRecyclerView() {
        adapter = ChatAdapter(requireContext(), messages)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun loadMessages() {
        generateDummyMessages()
        adapter.notifyDataSetChanged()
        recyclerView.scrollToPosition(messages.size - 1)
    }

    private fun setupSendButton() {
        sendButton.setOnClickListener {
            val messageText = messageInput.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val newMessage = ChatMessage(messageText, ChatMessage.TYPE_TEXT, Date())
                messages.add(newMessage)
                adapter.notifyItemInserted(messages.size - 1)
                recyclerView.scrollToPosition(messages.size - 1)
                messageInput.text.clear()
            }
        }
    }

    private fun generateDummyMessages() {
        val textMessages = listOf(
            "Hello!", "How are you?", "What's up?", "Nice to meet you!",
            "Have a great day! 😊", "Let's catch up soon! 👋", "I'm excited! 🎉",
            "That's awesome! 👍", "I'm busy right now 😓", "See you later! 👀"
        )
        val imageAssets = listOf("images/bitmap_1.jpg", "images/bitmap_2.jpg", "images/bitmap_3.jpg")

        for (i in 1..100) {
            val messageType = if (Random.nextFloat() < 0.2f) ChatMessage.TYPE_IMAGE else ChatMessage.TYPE_TEXT
            val content = if (messageType == ChatMessage.TYPE_TEXT) {
                textMessages[Random.nextInt(textMessages.size)]
            } else {
                imageAssets[Random.nextInt(imageAssets.size)]
            }
            val timestamp = Date(System.currentTimeMillis() - Random.nextLong() % (30L * 24 * 60 * 60 * 1000))
            messages.add(ChatMessage(content, messageType, timestamp))
        }

        messages.sortBy { it.timestamp }
    }

    private fun serializeMessages(messages: List<ChatMessage>): String {
        return messages.joinToString("|") { "${it.content}::${it.type}::${it.timestamp.time}" }
    }

    private fun deserializeMessages(serialized: String): List<ChatMessage> {
        return serialized.split("|").map {
            val (content, type, timestamp) = it.split("::")
            ChatMessage(content, type.toInt(), Date(timestamp.toLong()))
        }
    }
}