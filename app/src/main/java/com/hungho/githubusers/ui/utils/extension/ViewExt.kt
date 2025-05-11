package com.hungho.githubusers.ui.utils.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.hungho.githubusers.ui.utils.custom.OnSingleClickListener

// Avoid multiple clicks on a view
fun View.setOnSingleClickListener(onClickListener: View.OnClickListener?) {
    onClickListener?.also {
        setOnClickListener(OnSingleClickListener(it))
    } ?: setOnClickListener(null)
}

// Avoid multiple clicks on a view
fun View.setOnSingleClickListener(onClickListener: (View) -> Unit) {
    setOnSingleClickListener(View.OnClickListener { view ->
        onClickListener(view)
    })
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.visibleOrGone(isVisible: Boolean) {
    if (isVisible) {
        visible()
    } else {
        gone()
    }
}

fun View.visibleOrInvisible(isVisible: Boolean) {
    if (isVisible) {
        visible()
    } else {
        invisible()
    }
}

fun View.showKeyboard() {
    runCatching {
        requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(this, 0)
    }
}

