package com.manuelemr.redditapp.presentation.modules.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuelemr.redditapp.data.modules.posts.PostsRepository
import com.manuelemr.redditapp.data.modules.posts.models.Listing
import com.manuelemr.redditapp.data.modules.posts.models.PostModel
import com.manuelemr.redditapp.presentation.foundation.PreferencesHandler
import com.manuelemr.redditapp.presentation.foundation.RedditOAuthHandler
import com.manuelemr.redditapp.presentation.modules.posts.models.Post
import kotlinx.coroutines.launch

class PostsViewModel(private val postsRepository: PostsRepository,
    private val redditOAuthHandler: RedditOAuthHandler,
    private val preferencesHandler: PreferencesHandler) : ViewModel() {

    private val _posts = MutableLiveData<List<Post>>()
    private val _loading = MutableLiveData(false)
    private val _needsToLogin = MutableLiveData<String>()
    private var listing: Listing<PostModel>? = null

    val posts: LiveData<List<Post>> = _posts
    val loading: LiveData<Boolean> = _loading
    val needsToLogin: LiveData<String> = _needsToLogin

    fun getPosts() {
        _loading.postValue(true)
        viewModelScope.launch {
            postsRepository.getTopPosts()
                .fold({ postListing ->
                    listing = postListing.data
                    _posts.postValue(postListing.data.children.map { Post(it.data) })
                }, {
                    it.printStackTrace()
                    if (it.code() == 401) {
                        // show login screen again
                        preferencesHandler.token = null
                        _needsToLogin.postValue(redditOAuthHandler.authorizeURL)
                    }
                })
            _loading.postValue(false)
        }
    }
}
