package com.manuelemr.redditapp.presentation.foundation

import android.net.Uri
import kotlin.random.Random
import kotlin.random.nextInt

class RedditOAuthHandler(private val preferencesHandler: PreferencesHandler) {
    companion object {
        const val RedirectUri = "memapp://manuelemr.com/reddit"
    }

    private var lastState: String? = null
    val authorizeURL: String
        get() {
        val state = generateState()
        // strangely without the ".compact" it doesnt redirect on first login
         return "https://www.reddit.com/api/v1/authorize.compact?client_id=KuUP0BXhatcrFA&response_type=token&state=$state&redirect_uri=$RedirectUri&scope=read"
    }

    fun generateState(): String {
        val letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        //need 10 chars
        val state = (0 until 10).map { Random.nextInt(letters.indices) }
            .map(letters::get)
            .joinToString("")
        lastState = state
        return state
    }

    fun handleRedirectUri(uri: Uri): Boolean {

        val params = mutableMapOf<String, String>()
        uri.fragment?.split("&")?.forEach { param ->
            param.split("=")
                .takeIf { it.size == 2 }
                ?.let { params[it[0]] = it[1] }
        }

        if (lastState != params["state"]) {
            return false
        }
        preferencesHandler.token  = params["access_token"]
        return true
    }
}