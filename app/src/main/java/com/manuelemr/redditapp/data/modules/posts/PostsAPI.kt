package com.manuelemr.redditapp.data.modules.posts

import com.manuelemr.redditapp.data.modules.posts.models.Listing
import com.manuelemr.redditapp.data.modules.posts.models.PostModel
import com.manuelemr.redditapp.data.modules.posts.models.Thing
import retrofit2.http.GET
import retrofit2.http.Query

typealias PostListing = Thing<Listing<PostModel>>
interface PostsAPI {
    @GET("top")
    suspend fun getTopPosts(@Query("limit") limit: Int,
                   @Query("count") count: Int?,
                   @Query("after") after: String?): PostListing
}