package com.manuelemr.redditapp.presentation.foundation.extensions

import androidx.recyclerview.widget.DiffUtil
import com.manuelemr.redditapp.presentation.foundation.DefaultDiffUtilsCallback
import com.manuelemr.redditapp.presentation.modules.posts.PostsAdapter
import com.manuelemr.redditapp.presentation.modules.posts.models.Post

//TODO: Switch PostsAdapter class for a generic adapter and use Diffable as a constraint
fun PostsAdapter.calculateDiff(items: List<Post>) {
    val oldItems = this.items
    DefaultDiffUtilsCallback(oldItems, items)
        .let { DiffUtil.calculateDiff(it) }
        .dispatchUpdatesTo(this)
        .also { setItemsNoUpdate(items) }
}