package io.actinis.opengl.sample.messenger.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.actinis.opengl.sample.R
import io.actinis.opengl.sample.messenger.data.ChatMessage
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(private val context: Context, private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ChatMessage.TYPE_TEXT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_text_message, parent, false)
                TextMessageViewHolder(view)
            }

            ChatMessage.TYPE_IMAGE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_image_message, parent, false)
                ImageMessageViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is TextMessageViewHolder -> holder.bind(message)
            is ImageMessageViewHolder -> holder.bind(message)
        }
    }

    override fun getItemCount() = messages.size

    override fun getItemViewType(position: Int): Int {
        return messages[position].type
    }

    inner class TextMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val messageText: TextView = view.findViewById(R.id.messageText)
        private val timestampText: TextView = view.findViewById(R.id.timestampText)

        fun bind(message: ChatMessage) {
            messageText.text = message.content
            timestampText.text = dateFormat.format(message.timestamp)
        }
    }

    inner class ImageMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val messageImage: ImageView = view.findViewById(R.id.messageImage)
        private val timestampText: TextView = view.findViewById(R.id.timestampText)

        fun bind(message: ChatMessage) {
            // Load image from assets
            val inputStream = context.assets.open(message.content)
            val drawable = Drawable.createFromStream(inputStream, null)
            messageImage.setImageDrawable(drawable)
            timestampText.text = dateFormat.format(message.timestamp)
        }
    }
}