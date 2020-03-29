package com.manuelemr.redditapp.presentation.di

import android.content.Context
import com.manuelemr.redditapp.presentation.foundation.PreferencesHandler
import com.manuelemr.redditapp.presentation.foundation.RedditOAuthHandler
import com.manuelemr.redditapp.presentation.modules.posts.PostsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    single { PreferencesHandler(androidContext().getSharedPreferences("com.manuelemr.reddit", Context.MODE_PRIVATE)) }
    single { RedditOAuthHandler(get()) }

    //region ViewModels

    viewModel { PostsViewModel(get(), get(), get()) }

    //endregion
}