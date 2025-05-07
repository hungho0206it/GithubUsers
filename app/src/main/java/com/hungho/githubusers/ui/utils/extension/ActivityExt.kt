package com.hungho.githubusers.ui.utils.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import androidx.core.net.toUri

fun Activity.dismissKeyboard(windowToken: IBinder) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.openBrowser(url: String) {
    runCatching {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }
}