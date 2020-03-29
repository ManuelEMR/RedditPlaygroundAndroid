package com.manuelemr.redditapp.data.di

import com.google.gson.*
import com.manuelemr.redditapp.data.foundation.TokenInterceptor
import com.manuelemr.redditapp.data.modules.posts.PostsAPI
import com.manuelemr.redditapp.data.modules.posts.PostsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

private const val BaseUrl = "https://oauth.reddit.com/"
val dataModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .addInterceptor(TokenInterceptor(get()))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BaseUrl)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
    }

    single {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    //region Apis and Repos

    single {
        get<Retrofit>().create(PostsAPI::class.java)
    }

    single {
        PostsRepository(get())
    }

    //endregion
}