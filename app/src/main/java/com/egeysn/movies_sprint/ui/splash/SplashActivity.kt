package com.egeysn.movies_sprint.ui.splash

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import com.egeysn.movies_sprint.data.common.BaseActivity
import com.egeysn.movies_sprint.databinding.ActivitySplashBinding
import com.egeysn.movies_sprint.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private val viewModel: SplashActivityViewModel by viewModels()

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        waitAndNavigate()
    }

    private fun waitAndNavigate() {

        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {

                startActivity(MainActivity.createSimpleIntent(this@SplashActivity))
                finish()
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }
        })
    }
}
