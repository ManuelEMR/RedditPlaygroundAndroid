package com.manuelemr.redditapp.data.modules.posts

import com.github.kittinunf.result.coroutines.SuspendableResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class PostsRepository(private val api: PostsAPI) {

    suspend fun getTopPosts(count: Int? = null, after: String? = null): SuspendableResult<PostListing, HttpException> =
        withContext(Dispatchers.IO) {
            SuspendableResult.of<PostListing, HttpException> { api.getTopPosts( 20, count, after) }
        }
}