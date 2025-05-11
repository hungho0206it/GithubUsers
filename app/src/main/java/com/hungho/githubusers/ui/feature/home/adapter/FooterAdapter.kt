package com.hungho.githubusers.ui.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hungho.githubusers.databinding.ItemFooterBinding
import com.hungho.githubusers.ui.utils.extension.visibleOrGone

internal class FooterAdapter : LoadStateAdapter<FooterAdapter.FooterViewHolder>() {
    override fun onBindViewHolder(
        holder: FooterViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FooterViewHolder {
        return FooterViewHolder(
            ItemFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class FooterViewHolder(private val binding: ItemFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.progressBar.visibleOrGone(loadState == LoadState.Loading)
        }
    }
}