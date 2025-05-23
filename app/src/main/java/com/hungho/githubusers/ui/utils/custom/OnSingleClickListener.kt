package com.hungho.githubusers.ui.utils.custom

import android.view.View
import java.util.concurrent.atomic.AtomicBoolean

// Avoid multiple clicks on a view
internal class OnSingleClickListener(
    private val onClickListener: View.OnClickListener,
    private val intervalMs: Long = 250L
) : View.OnClickListener {
    private val canClick = AtomicBoolean(true)

    override fun onClick(view: View?) {
        if (canClick.getAndSet(false)) {
            view?.run {
                postDelayed({
                    canClick.set(true)
                }, intervalMs)
                onClickListener.onClick(view)
            }
        }
    }
}