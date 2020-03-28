package com.manuelemr.redditapp.presentation.foundation

class RedditOAuthHandler(private val preferencesHandler: PreferencesHandler) {
    companion object {
        const val RedirectUri = "memapp://manuelemr.com/reddit"
    }

    fun generateState(): String {
        val letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        letters.map {  }
    }
}