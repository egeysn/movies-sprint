package com.egeysn.movies_sprint.data.common

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.egeysn.movies_sprint.utils.GeneralUtils
import com.egeysn.movies_sprint.utils.LoadingHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity() : AppCompatActivity() {

    @Inject
    lateinit var loadingHelper: LoadingHelper

    @Inject
    lateinit var utils: GeneralUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)

        try {
            loadingHelper.register(this)
        } catch (e: Exception) {
        }
    }

    override fun onResume() {
        super.onResume()
    }

    fun showLoading() {
        loadingHelper.showDialog()
    }

    fun hideLoading() {
        loadingHelper.hideDialog()
    }
}
