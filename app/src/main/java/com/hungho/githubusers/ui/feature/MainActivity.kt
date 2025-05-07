package com.hungho.githubusers.ui.feature

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hungho.githubusers.R
import com.hungho.githubusers.databinding.ActivityMainBinding
import com.hungho.githubusers.ui.dialog.loading.LoadingDialogFragment
import com.hungho.githubusers.ui.utils.extension.dismissIfAdded
import com.hungho.githubusers.ui.utils.extension.showIfNotExist

class MainActivity : AppCompatActivity() {
    private var loadingDialogFragment: LoadingDialogFragment? = null

    private var binding: ActivityMainBinding? = null
    private var navController: NavController? = null
    private var navHostFragment: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setWhiteStatusBarWithDarkIcons()
        initNavigation()
    }

    private fun initNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment
        navController = navHostFragment?.navController
    }

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

    private fun setWhiteStatusBarWithDarkIcons() {
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)

        // Set dark icons (black text) on the status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}