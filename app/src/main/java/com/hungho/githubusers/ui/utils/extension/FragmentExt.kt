package com.hungho.githubusers.ui.utils.extension

import androidx.fragment.app.Fragment

fun Fragment.dismissKeyboard() {
    view?.run {
        windowToken?.let { activity?.dismissKeyboard(it) }
    }
}