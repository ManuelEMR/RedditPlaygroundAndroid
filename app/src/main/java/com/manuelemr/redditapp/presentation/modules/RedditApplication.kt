package com.manuelemr.redditapp.presentation.modules

import android.app.Application
import com.manuelemr.redditapp.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RedditApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RedditApplication)
            modules(listOf(presentationModule))
        }
    }
}