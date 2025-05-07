package com.hungho.githubusers.ui.utils.extension

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import timber.log.Timber

fun DialogFragment.showIfNotExist(fragmentManager: FragmentManager, tag: String?) {
    val isNotExist = fragmentManager.findFragmentByTag(tag) == null
    if (isNotExist) {
        try {
            showNow(fragmentManager, tag)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}

fun DialogFragment.dismissIfAdded() {
    if (isAdded) dismissAllowingStateLoss()
}