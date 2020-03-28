package com.manuelemr.redditapp.presentation.modules.posts

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.manuelemr.redditapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostsFragment : Fragment(R.layout.fragment_posts) {

    companion object {
        fun newInstance() = PostsFragment()
    }

    private val viewModel: PostsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
