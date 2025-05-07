package com.hungho.githubusers.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding
import com.hungho.githubusers.R

abstract class BaseFullScreenDialogFragment<VB : ViewBinding> : BaseDialogFragment<VB>() {

    open fun onFullscreenHandling(top: Int, bottom: Int) {}

    open var isFullScreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog_FullScreen)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setDecorFitsSystemWindows(dialog)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = onCreateViewBinding(inflater, container)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildFullscreen()
    }

    private fun setDecorFitsSystemWindows(dialog: Dialog) {
        dialog.window?.let { window ->
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    private fun buildFullscreen() {
        viewBinding?.root?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { view, windowInsets ->
                val imeVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime())
                val imeHeight = windowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                val statusBar = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                val top = statusBar.top
                val bottom = if (imeVisible) imeHeight else statusBar.bottom
                if (isFullScreen) {
                    view.updatePadding(
                        top = 0,
                        bottom = 0,
                    )
                    onFullscreenHandling(top, bottom)
                } else {
                    view.updatePadding(
                        top = top,
                        bottom = bottom,
                    )
                }
                WindowInsetsCompat.CONSUMED
            }
        }
    }
}
