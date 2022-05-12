package com.egeysn.video_games_sprint.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.egeysn.movies_sprint.R
import com.naylalabs.kotlinbaseproject.common.BaseActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private val viewModel: SplashActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        waitAndNavigate()
    }

    private fun waitAndNavigate() {
        showLoading()
        val handler = Handler()
        handler.postDelayed(
            Runnable {
                //startActivity(MainActivity.createSimpleIntent(this))
            },
            2000
        )
    }
}
