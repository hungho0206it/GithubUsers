package com.hungho.githubusers.ui.dialog.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hungho.githubusers.R
import com.hungho.githubusers.databinding.DialogLoadingBinding
import com.hungho.githubusers.ui.base.BaseFullScreenDialogFragment

internal class LoadingDialogFragment : BaseFullScreenDialogFragment<DialogLoadingBinding>() {

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogLoadingBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_Dialog_Loading)
        isCancelable = false
    }

    companion object {
        const val TAG = "LoadingDialogFragment"

        fun newInstance() = LoadingDialogFragment()
    }
}