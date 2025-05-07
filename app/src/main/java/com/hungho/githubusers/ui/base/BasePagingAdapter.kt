package com.hungho.githubusers.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BasePagingAdapter<Item : Any, VH : RecyclerView.ViewHolder>(diffUtil: DiffUtil.ItemCallback<Item>) :
    PagingDataAdapter<Item, VH>(diffUtil) {

    abstract fun onCreateViewHolder(
        viewType: Int,
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): VH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateViewHolder(
            layoutInflater = LayoutInflater.from(parent.context),
            parent = parent,
            viewType = viewType
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let {
            val bindingViewHolder = holder as? BaseViewHolder<Item>
            bindingViewHolder?.bind(it, position)
        }
    }
}