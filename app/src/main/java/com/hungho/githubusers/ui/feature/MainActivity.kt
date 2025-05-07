package com.hungho.githubusers.ui.feature

import androidx.appcompat.app.AppCompatActivity
import com.hungho.githubusers.ui.dialog.loading.LoadingDialogFragment
import com.hungho.githubusers.ui.utils.extension.dismissIfAdded
import com.hungho.githubusers.ui.utils.extension.showIfNotExist

class MainActivity : AppCompatActivity() {
    private var loadingDialogFragment: LoadingDialogFragment? = null

    fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            showLoading()
        } else {
            dismissLoading()
        }
    }

    private fun showLoading() {
        if (loadingDialogFragment == null) {
            loadingDialogFragment = LoadingDialogFragment.Companion.newInstance()
        }
        loadingDialogFragment?.showIfNotExist(supportFragmentManager, LoadingDialogFragment.Companion.TAG)
    }

    private fun dismissLoading() {
        loadingDialogFragment?.dismissIfAdded()
        loadingDialogFragment = null
    }
}