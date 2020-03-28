package com.manuelemr.redditapp.presentation.modules.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manuelemr.redditapp.data.modules.posts.PostsRepository
import com.manuelemr.redditapp.data.modules.posts.models.Listing
import com.manuelemr.redditapp.data.modules.posts.models.PostModel
import com.manuelemr.redditapp.presentation.modules.posts.models.Post
import kotlinx.coroutines.launch

class PostsViewModel(private val postsRepository: PostsRepository) : ViewModel() {

    private val _posts = MutableLiveData<List<Post>>()
    private val _loading = MutableLiveData(false)
    private var listing: Listing<PostModel>? = null

    val posts: LiveData<List<Post>> = _posts
    val loading: LiveData<Boolean> = _loading

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
                    }
                })
            _loading.postValue(false)
        }
    }
}
