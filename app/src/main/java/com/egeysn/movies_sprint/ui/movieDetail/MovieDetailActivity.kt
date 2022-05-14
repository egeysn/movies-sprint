package com.egeysn.movies_sprint.ui.movieDetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.egeysn.movies_sprint.data.common.BaseActivity
import com.egeysn.movies_sprint.databinding.ActivityMovieDetailBinding

@SuppressLint("CustomSplashScreen")
class MovieDetailActivity : BaseActivity() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adjustUI()
        listeners()
    }

    private fun adjustUI() {
    }

    private fun listeners() {
    }

    private fun destroy() {
    }

    companion object {
        fun createSimpleIntent(context: Context): Intent {
            return Intent(context, MovieDetailActivity::class.java)
        }
    }
}
