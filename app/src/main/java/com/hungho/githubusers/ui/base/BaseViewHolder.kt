package com.hungho.githubusers.ui.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hungho.githubusers.ui.utils.extension.setOnSingleClickListener

internal abstract class BaseViewHolder<Item>(
    itemView: View,
    onItemClickListener: ((Item) -> Unit)? = null,
) : RecyclerView.ViewHolder(itemView) {
    val context: Context
        get() = itemView.context

    var item: Item? = null

    protected abstract fun onBind(item: Item, position: Int)

    init {
        onItemClickListener?.let {
            this.itemView.setOnSingleClickListener {
                item?.let(onItemClickListener)
            }
        }

    }

    fun bind(item: Item, position: Int) {
        this.item = item
        onBind(item, position)
    }


    fun bind(item: Item, position: Int, payloads: List<Any>) {
        this.item = item
        onBind(item, position, payloads)
    }

    open fun onBind(item: Item, position: Int, payloads: List<Any>) {
        onBind(item, position)
    }
}