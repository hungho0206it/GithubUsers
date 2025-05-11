package com.hungho.githubusers.ui.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.hungho.domain.model.UserModel
import com.hungho.githubusers.databinding.ItemUserBinding
import com.hungho.githubusers.ui.base.BasePagingAdapter
import com.hungho.githubusers.ui.base.BaseViewHolder
import com.hungho.githubusers.ui.utils.custom.ImageLoader
import com.hungho.githubusers.ui.utils.extension.setOnSingleClickListener

internal class UserPagingAdapter(
    private val onItemClick: (UserModel) -> Unit,
    private val onHtmlUrlClick: (String) -> Unit
) : BasePagingAdapter<UserModel, UserPagingAdapter.UserViewHolder>(object :
    DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(
        oldItem: UserModel,
        newItem: UserModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UserModel,
        newItem: UserModel
    ): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(
        viewType: Int,
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onViewRecycled(holder: UserViewHolder) {
        super.onViewRecycled(holder)
        ImageLoader.clear(holder.binding.ivAvatar)
    }

    inner class UserViewHolder(val binding: ItemUserBinding) :
        BaseViewHolder<UserModel>(binding.root, onItemClick) {
        override fun onBind(item: UserModel, position: Int) {
            binding.apply {
                tvUsername.text = item.username
                ImageLoader.load(ivAvatar, item.avatarUrl)
                tvUserLink.text = item.htmlUrl

                tvUserLink.setOnSingleClickListener {
                    onHtmlUrlClick(item.htmlUrl)
                }
            }
        }
    }
}