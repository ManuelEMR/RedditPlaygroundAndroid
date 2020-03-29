package com.manuelemr.redditapp.presentation.modules.posts.models

import com.manuelemr.redditapp.data.modules.posts.models.PostModel
import com.manuelemr.redditapp.presentation.foundation.Diffable
import java.util.*

data class Post(
    val title: String,
    val author: String,
    val createdAt: Date,
    val thumbnail: String?,
    val numberOfComments: String,
    val read: Boolean
): Diffable {

    override var identifier: String = title + author
    override fun areContentsTheSame(other: Diffable): Boolean = other is Post && other == this

    constructor(model: PostModel):this(model.title,
        model.author,
        Date(model.created * 1000),
        model.thumbnail,
        model.numComments.toString(),
        false)
}
