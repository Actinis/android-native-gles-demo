package io.actinis.opengl.sample.messenger.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.actinis.opengl.sample.R
import io.actinis.opengl.sample.messenger.adapter.ChatListAdapter
import io.actinis.opengl.sample.messenger.data.Chat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class ChatListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var loader: View
    private lateinit var adapter: ChatListAdapter

    var onChatClick: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.chatRecyclerView)
        loader = view.findViewById(R.id.loader)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ChatListAdapter(requireContext(), ::onChatClick)
        recyclerView.adapter = adapter

        loadChats()
    }

    private fun onChatClick(chat: Chat) {
        onChatClick()
//        showAlertDialog()
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Alert Dialog Title")
        builder.setMessage("This is the message of the Alert Dialog.")

        builder.setPositiveButton("OK") { dialog, which ->
            Toast.makeText(requireContext(), "OK button clicked", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            Toast.makeText(requireContext(), "Cancel button clicked", Toast.LENGTH_SHORT).show()
        }

        builder.setNeutralButton("Remind me later") { dialog, which ->
            Toast.makeText(requireContext(), "Remind me later button clicked", Toast.LENGTH_SHORT).show()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun loadChats() {
        CoroutineScope(Dispatchers.Main).launch {
            loader.visibility = View.VISIBLE
            delay(500) // Simulate network delay
            val chats = generateMockedChats()
            adapter.setChats(chats)
            loader.visibility = View.GONE
        }
    }

    private fun generateMockedChats(): List<Chat> {
        val names = listOf("Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Henry")
        val messages = listOf(
            "Hey, how are you?",
            "Did you see the latest movie?",
            "I'm working on a new project.",
            "Let's meet for coffee sometime.",
            "Can you help me with this problem?",
            "Happy birthday!",
            "Remember to bring the documents tomorrow.",
            "I'm going on vacation next week."
        )

        return List(20) { index ->
            Chat(
                id = index,
                name = names.random(),
                lastMessage = messages.random(),
                timestamp = generateRandomTimestamp(),
                unreadCount = Random.nextInt(0, 10),
                avatarPath = getRandomAvatarPath()
            )
        }
    }

    private fun generateRandomTimestamp(): String {
        val hour = Random.nextInt(1, 13)
        val minute = Random.nextInt(0, 60)
        val amPm = if (Random.nextBoolean()) "AM" else "PM"
        return String.format("%d:%02d %s", hour, minute, amPm)
    }

    private fun getRandomAvatarPath(): String {
        val avatarsPaths = listOf(
            "images/avatar_1.jpg",
            "images/avatar_2.jpg",
            "images/avatar_3.jpg",
            "images/avatar_4.jpg",
            "images/avatar_5.png",
        )
        return avatarsPaths.random()
    }
}
