package io.actinis.opengl.sample.messenger.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.actinis.opengl.sample.R
import io.actinis.opengl.sample.messenger.data.Chat

class ChatListAdapter(
    private val context: Context,
    private val onItemClick: (chat: Chat) -> Unit,
) : RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    private var chats: List<Chat> = emptyList()

    private var avatarsBitmaps = mutableMapOf<String, Bitmap>()

    fun setChats(chats: List<Chat>) {
        this.chats = chats
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.view_item_chat, parent,
            false
        )
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chats[position])
    }

    override fun getItemCount(): Int = chats.size

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val lastMessageTextView: TextView = itemView.findViewById(R.id.lastMessageTextView)
        private val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)
        private val unreadCountTextView: TextView = itemView.findViewById(R.id.unreadCountTextView)

        fun bind(chat: Chat) {
            itemView.tag = chat

            itemView.setOnClickListener {
                onItemClick(chat)
            }

            var bitmap = avatarsBitmaps[chat.avatarPath]
            if (bitmap == null) {
                context.assets.open(chat.avatarPath).use { stream ->
                    bitmap = BitmapFactory.decodeStream(stream)
                    avatarsBitmaps[chat.avatarPath] = bitmap as Bitmap
                }
            }

            avatarImageView.setImageBitmap(bitmap)
            nameTextView.text = chat.name
            lastMessageTextView.text = chat.lastMessage
            timestampTextView.text = chat.timestamp

            if (chat.unreadCount > 0) {
                unreadCountTextView.visibility = View.VISIBLE
                unreadCountTextView.text = chat.unreadCount.toString()
            } else {
                unreadCountTextView.visibility = View.GONE
            }
        }
    }
}
