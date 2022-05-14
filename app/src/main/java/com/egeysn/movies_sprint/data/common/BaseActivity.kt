package com.egeysn.movies_sprint.data.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.egeysn.movies_sprint.utils.GeneralUtils
import com.egeysn.movies_sprint.utils.LoadingHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var utils: GeneralUtils

    private lateinit var loadingHelper: LoadingHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingHelper = LoadingHelper.getInstance(this@BaseActivity)
    }

    fun showLoading() {
        runOnUiThread {
            loadingHelper.showDialog()
        }
    }

    fun hideLoading() {
        runOnUiThread { loadingHelper.hideDialog() }
    }
}
