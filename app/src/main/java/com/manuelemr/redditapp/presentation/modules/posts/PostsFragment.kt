package com.manuelemr.redditapp.presentation.modules.posts

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.observe

import com.manuelemr.redditapp.R
import kotlinx.android.synthetic.main.fragment_posts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostsFragment : Fragment(R.layout.fragment_posts) {

    companion object {
        fun newInstance() = PostsFragment()
    }

    private val viewModel: PostsViewModel by viewModel()
    private var adapter: PostsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupBindings()
    }

    private fun setupViews() {
        postsList.adapter = PostsAdapter().also { adapter = it }
    }

    private fun setupBindings() {
        viewModel.getPosts()

        viewModel.posts.observe(viewLifecycleOwner) {
            adapter?.items = it
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            progressBar.isVisible = it
        }
    }
}
