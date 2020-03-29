package com.manuelemr.redditapp.presentation.modules.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manuelemr.redditapp.R
import com.manuelemr.redditapp.presentation.foundation.extensions.load
import com.manuelemr.redditapp.presentation.modules.posts.models.Post
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_post.*
import java.util.*
import java.util.concurrent.TimeUnit

class PostsAdapter(private val onPostClickListener: (Post) -> Unit): RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    private var _items: List<Post> = emptyList()

    var items: List<Post>
        get() = _items
        set(value) {
            _items = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
            .let { ViewHolder(it) }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setupWith(_items[position])
        holder.itemView.setOnClickListener {
            onPostClickListener(_items[position])
        }
    }

    class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun setupWith(post: Post) {
            titleText.text = post.title
            authorText.text = post.author
            commentsCountText.text = post.numberOfComments
            postImageView.load(post.thumbnail)

            val now = Date()
            val diffInMillis = now.time - post.createdAt.time
            val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
            createdText.text = containerView.resources.getString(R.string.post_created_at_format, diffInHours)
        }
    }
}