package com.hungho.githubusers.ui.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.hungho.data.helper.extension.toFailure
import com.hungho.domain.model.UserModel
import com.hungho.githubusers.R
import com.hungho.githubusers.databinding.FragmentHomeBinding
import com.hungho.githubusers.ui.base.BaseFragment
import com.hungho.githubusers.ui.feature.home.adapter.FooterAdapter
import com.hungho.githubusers.ui.feature.home.adapter.UserPagingAdapter
import com.hungho.githubusers.ui.feature.user_detail.UserDetailsFragment
import com.hungho.githubusers.ui.utils.extension.openBrowser
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModel()

    private val userPagingAdapter by lazy {
        UserPagingAdapter(::handleOnUserItemClick, ::handleOnUserHtmlUrlClick)
    }

    private var isRefreshing: Boolean = false


    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        initRecyclerView()
        initSwipeToRefresh()
    }

    override fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userPagingSource.collectLatest {
                    isRefreshing = true
                    userPagingAdapter.submitData(it)
                }
            }
        }
    }

    private fun initRecyclerView() {
        viewBinding?.rvUsers?.apply {
            adapter = userPagingAdapter.withLoadStateFooter(FooterAdapter())
            userPagingAdapter.addLoadStateListener { loadState ->
                if (isRefreshing && loadState.source.refresh is LoadState.NotLoading) {
                    val refreshError = loadState.refresh as? LoadState.Error
                    val appendError = loadState.append as? LoadState.Error
                    val failure = when {
                        refreshError != null -> {
                            refreshError.error.toFailure()
                        }

                        appendError != null -> {
                            appendError.error.toFailure()
                        }

                        else -> null
                    }
                    if (failure != null) {
                        onError(failure)
                        isRefreshing = false
                    }
                }
            }
        }
    }

    private fun initSwipeToRefresh() {
        viewBinding?.swipeRefreshLayout?.setOnRefreshListener {
            userPagingAdapter.refresh()
            viewBinding?.swipeRefreshLayout?.isRefreshing = false
        }
    }

    private fun handleOnUserItemClick(item: UserModel) {
        findNavController().navigate(
            R.id.action_homeFragment_to_userDetailsFragment, bundleOf(
                UserDetailsFragment.KEY_USERNAME to item.username
            )
        )
    }

    private fun handleOnUserHtmlUrlClick(url: String) {
        activity?.openBrowser(url)
    }
}