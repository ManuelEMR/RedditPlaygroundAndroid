package com.manuelemr.redditapp.data.foundation

import com.manuelemr.redditapp.presentation.foundation.PreferencesHandler
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val preferenceHandler: PreferencesHandler) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request().newBuilder()
            .apply {
                preferenceHandler.token
                    ?.let { addHeader("Authorization", "bearer $it") }
            }
            .build()
            .let { chain.proceed(it) }
}