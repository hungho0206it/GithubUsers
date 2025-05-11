package com.hungho.githubusers.ui.base

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.hungho.domain.model.error.Failure
import com.hungho.githubusers.R
import com.hungho.githubusers.ui.feature.MainActivity
import com.hungho.githubusers.ui.utils.extension.dismissKeyboard
import timber.log.Timber

internal abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {
    abstract val viewModel: VM?

    private val mainActivity by lazy { activity as? MainActivity }

    private val baseViewModel by lazy { viewModel as? BaseViewModel }

    open var isFullScreen = false

    var viewBinding: VB? = null

    protected abstract fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected abstract fun initViews()

    protected abstract fun initViewModel()

    override fun onAttach(context: Context) {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onCreate")
        super.onCreate(savedInstanceState)
        addBackPressedCallback()
        observeBaseViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onCreateView")
        viewBinding = onCreateViewBinding(inflater, container)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        buildFullscreen()
        initViews()
        initViewModel()
    }

    override fun onStart() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onStart")
        super.onStart()
        onBackPressedCallback.isEnabled = true
    }

    override fun onResume() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onResume")
        super.onResume()
    }

    override fun onPause() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onPause")
        super.onPause()
    }

    override fun onStop() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onDestroyView")
        dismissKeyboard()
        super.onDestroyView()
    }

    override fun onDestroy() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Timber.tag(LIFECYCLE_TAG).i("${this::class.simpleName} onDetach")
        super.onDetach()
    }

    private fun addBackPressedCallback() {
        activity?.onBackPressedDispatcher?.addCallback(this, onBackPressedCallback)
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

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (onBackPressed()) {
                isEnabled = false
                findNavController().navigateUp()
            }
        }
    }

    private fun observeBaseViewModel() {
        baseViewModel?.loading?.observe(this) { isLoading ->
            onLoading(isLoading = isLoading)
        }
        baseViewModel?.error?.observe(this) { throwable ->
            if (throwable is Failure) {
                onError(throwable)
            }
        }
    }

    open fun onBackPressed(): Boolean = true

    open fun onFullscreenHandling(top: Int, bottom: Int) {}

    open fun onError(failure: Failure) {
        val message = when (failure) {
            is Failure.ApiFailure -> failure.errorMessage
            is Failure.NetworkFailure -> failure.errorMessage
            is Failure.UnauthorizedFailure -> {
                return
            }

            else -> {
                getString(R.string.something_went_wrong)
            }
        }
        onShowErrorMessage(message)
    }

    open fun onCloseErrorMessage() = Unit

    // Show error message dialog
    open fun onShowErrorMessage(message: String) {
        AlertDialog.Builder(context)
            .setTitle(getString(R.string.error))
            .setMessage(message)
            .setPositiveButton(R.string.text_ok) { dialog, _ ->
                onCloseErrorMessage()
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    open fun onLoading(isLoading: Boolean) {
        mainActivity?.onLoading(isLoading)
    }

    companion object {
        private const val LIFECYCLE_TAG = "FragmentLifecycle"
    }
}