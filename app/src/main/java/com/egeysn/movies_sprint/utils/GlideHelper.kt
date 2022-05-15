package com.egeysn.movies_sprint.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.egeysn.movies_sprint.BuildConfig
import com.egeysn.movies_sprint.R

object GlideHelper {

    @Synchronized
    fun loadImage(context: Context?, url: String?, view: ImageView) {
        // create a ProgressDrawable object which we will show as placeholder
        val progress = context?.let { CircularProgressDrawable(it) }
        progress?.setColorSchemeColors(
            R.color.white,
            R.color.primary
        )
        progress?.centerRadius = 30f
        progress?.strokeWidth = 5f
        progress?.start()
        Glide.with(context!!)
            .load(BuildConfig.BASE_IMAGE_URL + (url ?: ""))
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.image_error_place_holder)
            .placeholder(R.drawable.image_error_place_holder)
            .into(view)
    }
}
