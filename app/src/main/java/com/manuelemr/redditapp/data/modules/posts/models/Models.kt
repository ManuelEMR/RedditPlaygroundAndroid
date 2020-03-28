package com.manuelemr.redditapp.data.modules.posts.models

import com.google.gson.annotations.SerializedName

data class Thing<T>(
    val id: String?,
    val name: String?,
    val kind: ThingKind,
    val data: T
)

enum class ThingKind {
    @SerializedName("Listing") LISTING,
    @SerializedName("t1") T1,
    @SerializedName("t2") T2,
    @SerializedName("t3") T3,
    @SerializedName("more") MORE
}

data class Listing<T>(
    val before: String?,
    val after: String?,
    val children: List<Thing<T>>
)

data class PostModel(
    val title: String,
    val author: String,
    val created: Long,
    val thumbnail: String?,
    val numComments: Int,
    val name: String
)