package com.manuelemr.redditapp.data.modules.posts

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostsRepository(private val api: PostsAPI) {

    suspend fun getTopPosts(count: Int? = null, after: String? = null): PostListing = withContext(Dispatchers.IO) {
        api.getTopPosts( 20, count, after)
    }
}