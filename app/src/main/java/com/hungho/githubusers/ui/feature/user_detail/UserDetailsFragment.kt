package com.hungho.githubusers.ui.feature.user_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hungho.domain.model.UserDetailsModel
import com.hungho.githubusers.R
import com.hungho.githubusers.databinding.FragmentUserDetailsBinding
import com.hungho.githubusers.ui.base.BaseFragment
import com.hungho.githubusers.ui.utils.custom.ImageLoader
import com.hungho.githubusers.ui.utils.extension.openBrowser
import com.hungho.githubusers.ui.utils.extension.setOnSingleClickListener
import com.hungho.githubusers.ui.utils.extension.visibleOrGone
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding, UserDetailsViewModel>() {
    override val viewModel: UserDetailsViewModel by viewModel()

    private val username: String by lazy {
        arguments?.getString(KEY_USERNAME).orEmpty()
    }

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserDetailsBinding {
        return FragmentUserDetailsBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        viewBinding?.apply {
            toolbar.setOnBackClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun initViewModel() {
        if (username.isEmpty()) {
            findNavController().navigateUp()
            return
        }
        viewModel.getUserDetails(username)
        viewModel.userDetails.observe(viewLifecycleOwner) {
            updateUi(userDetails = it)
        }
    }

    override fun onCloseErrorMessage() {
        findNavController().navigateUp()
    }

    private fun updateUi(userDetails: UserDetailsModel) {
        viewBinding?.apply {
            val followers = if (userDetails.follower > 100) {
                getString(R.string.over_100)
            } else {
                userDetails.follower.toString()
            }
            val followings = if (userDetails.following > 100) {
                getString(R.string.over_100)
            } else {
                userDetails.following.toString()
            }
            tvFollower.text = followers
            tvFollowing.text = followings
            tvFollowerLabel.text =
                if (userDetails.follower > 1) getString(R.string.followers) else getString(R.string.follower)
            tvFollowingLabel.text =
                if (userDetails.following > 1) getString(R.string.followings) else getString(R.string.following)
            tvUsername.text = userDetails.username
            tvUserLocation.text = userDetails.location
            groupLocation.visibleOrGone(userDetails.location.isNotEmpty())
            ImageLoader.load(ivAvatar, userDetails.avatarUrl)
            val userLink = userDetails.blog.ifEmpty { userDetails.htmlUrl }
            tvUserLink.text = userLink
            tvUserLink.setOnSingleClickListener {
                activity?.openBrowser(userLink)
            }
        }
    }

    companion object {
        const val KEY_USERNAME = "username"
    }
}