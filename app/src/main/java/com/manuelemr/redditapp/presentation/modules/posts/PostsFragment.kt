package com.manuelemr.redditapp.presentation.modules.posts

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.observe

import com.manuelemr.redditapp.R
import com.manuelemr.redditapp.presentation.modules.posts.models.Post
import kotlinx.android.synthetic.main.fragment_posts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostsFragment : Fragment(R.layout.fragment_posts) {

    companion object {
        const val LIST_STATE = "list_state"

        fun newInstance() = PostsFragment()
    }

    private val viewModel: PostsViewModel by viewModel()
    private var adapter: PostsAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.getParcelable<Parcelable>(LIST_STATE)?.let {
            postsList.layoutManager?.onRestoreInstanceState(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupBindings()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        postsList.layoutManager?.onSaveInstanceState()?.let {
            outState.putParcelable(LIST_STATE, it)
        }
        super.onSaveInstanceState(outState)
    }

    private fun setupViews() {
        postsList.adapter = PostsAdapter(::onPostClick).also { adapter = it }

        refreshLayout.setOnRefreshListener {
            viewModel.getPosts()
        }
    }

    private fun setupBindings() {
        viewModel.posts.observe(viewLifecycleOwner) {
            adapter?.items = it
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            progressBar.isVisible = it
        }

        viewModel.needsToLogin.observe(viewLifecycleOwner) {
            Intent(Intent.ACTION_VIEW)
                .apply { data = Uri.parse(it) }
                .let { startActivity(it) }
        }
    }

    private fun onPostClick(post: Post) {
        post.thumbnail?.let {
            Intent(Intent.ACTION_VIEW)
                .apply { data = Uri.parse(it) }
                .let { startActivity(it) }
        }
    }
}
