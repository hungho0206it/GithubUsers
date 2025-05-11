package com.hungho.githubusers.ui.utils.custom

import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.hungho.githubusers.R

internal object ImageLoader {

    // Load an image into ImageView with full options.
    fun load(
        imageView: ImageView,
        uri: String,
        diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
        placeholder: Int = R.mipmap.ic_launcher,
        error: Int = R.mipmap.ic_launcher,
        width: Int = Target.SIZE_ORIGINAL,
        height: Int = Target.SIZE_ORIGINAL,
    ) {
        val context = imageView.context ?: return
        val options = RequestOptions()
            .diskCacheStrategy(diskCacheStrategy)
            .override(width, height)
            .placeholder(placeholder)
            .error(error)
        val uri = try {
            uri.toUri()
        } catch (_: Exception) {
            null
        }
        uri?.let {
            Glide.with(context)
                .load(uri)
                .apply(options)
                .into(imageView)
        }
    }

    // Clears the image currently being loaded into the provided [imageView].
    fun clear(imageView: ImageView) {
        val context = imageView.context ?: return
        Glide.with(context).clear(imageView)
    }
}