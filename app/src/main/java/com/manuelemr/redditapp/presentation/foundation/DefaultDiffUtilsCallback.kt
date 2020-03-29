package com.manuelemr.redditapp.presentation.foundation

import androidx.recyclerview.widget.DiffUtil

interface Diffable {
    var identifier: String
    fun areContentsTheSame(other: Diffable): Boolean
}

class DefaultDiffUtilsCallback<T : Diffable>(
    private val oldList: List<T>,
    private val newList: List<T>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].identifier == newList[newItemPosition].identifier

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].areContentsTheSame(newList[newItemPosition])
}
