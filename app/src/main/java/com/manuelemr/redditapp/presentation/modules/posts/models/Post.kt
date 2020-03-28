package com.manuelemr.redditapp.presentation.modules.posts.models

import com.manuelemr.redditapp.data.modules.posts.models.PostModel
import java.util.*

data class Post(
    val title: String,
    val author: String,
    val createdAt: Date,
    val thumbnail: String?,
    val numberOfComments: String,
    val read: Boolean
) {
    constructor(model: PostModel):this(model.title,
        model.author,
        Date(model.created),
        model.thumbnail,
        model.numComments.toString(),
        false)
}
