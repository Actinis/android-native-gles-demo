package io.actinis.opengl.sample.messenger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import io.actinis.opengl.sample.R
import io.actinis.opengl.sample.messenger.fragment.ChatFragment
import io.actinis.opengl.sample.messenger.fragment.ChatListFragment

class MessengerActivity : AppCompatActivity() {

    private lateinit var chatListFragment: ChatListFragment
    private lateinit var chatFragment: ChatFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_messenger)

        chatListFragment = ChatListFragment().apply {
            onChatClick = ::showChat
        }

        chatFragment = ChatFragment()


        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragmentContainer, chatListFragment)
        }
    }

    private fun showChat() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, chatFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
