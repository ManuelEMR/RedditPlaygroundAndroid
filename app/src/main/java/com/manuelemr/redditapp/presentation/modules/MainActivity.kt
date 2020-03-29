package com.manuelemr.redditapp.presentation.modules

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.manuelemr.redditapp.R
import com.manuelemr.redditapp.presentation.foundation.PreferencesHandler
import com.manuelemr.redditapp.presentation.foundation.RedditOAuthHandler
import com.manuelemr.redditapp.presentation.modules.posts.PostsFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val redditOAuthHandler: RedditOAuthHandler by inject()
    private val preferencesHandler: PreferencesHandler by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            showFirstScreen()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.data?.let {
            redditOAuthHandler.handleRedirectUri(it)
        }?.let {
            if (it) {
                openPosts()
            } else {
                Toast.makeText(this, "Wrong redirect URI", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showFirstScreen() {
        if (preferencesHandler.token != null) {
            openPosts()
        } else {
            Intent(Intent.ACTION_VIEW)
                .apply { data = Uri.parse(redditOAuthHandler.authorizeURL) }
                .let { startActivity(it) }
        }
    }

    private fun openPosts() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PostsFragment.newInstance())
            .commit()
    }
}
