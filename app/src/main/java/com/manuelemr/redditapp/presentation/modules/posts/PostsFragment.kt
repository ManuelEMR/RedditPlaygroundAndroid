package com.manuelemr.redditapp.presentation.modules.posts

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator

import com.manuelemr.redditapp.R
import com.manuelemr.redditapp.presentation.foundation.extensions.calculateDiff
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupBindings()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.getParcelable<Parcelable>(LIST_STATE)?.let {
            postsList.layoutManager?.onRestoreInstanceState(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        postsList.layoutManager?.onSaveInstanceState()?.let {
            outState.putParcelable(LIST_STATE, it)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_posts, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionDeleteAll -> viewModel.deleteAll().let { true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViews() {
        postsList.adapter = PostsAdapter(::onPostClick).also { adapter = it }
        postsList.itemAnimator = DefaultItemAnimator()

        refreshLayout.setOnRefreshListener {
            viewModel.getPosts()
        }
    }

    private fun setupBindings() {
        viewModel.posts.observe(viewLifecycleOwner) {
            adapter?.calculateDiff(it)
            refreshLayout.isRefreshing = false
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
