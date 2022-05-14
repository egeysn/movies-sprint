package com.egeysn.movies_sprint.utils

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("InflateParams")
class GeneralUtils(private val context: Context) {

    fun dpToPx(dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: GeneralUtils? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: GeneralUtils(context).also { instance = it }
        }
    }
}
