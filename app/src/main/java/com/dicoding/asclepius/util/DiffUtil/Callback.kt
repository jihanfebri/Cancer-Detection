package com.dicoding.asclepius.util.DiffUtil

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.asclepius.data.remote.response.ArticlesItem

class NewsDiffCallback(private val oldList: List<ArticlesItem>, private val newList: List<ArticlesItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].url == newList[newItemPosition].url
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
