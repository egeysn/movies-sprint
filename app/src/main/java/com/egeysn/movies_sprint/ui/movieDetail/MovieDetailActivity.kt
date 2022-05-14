package com.egeysn.movies_sprint.ui.movieDetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.egeysn.movies_sprint.data.common.BaseActivity
import com.egeysn.movies_sprint.databinding.ActivityMovieDetailBinding
import com.egeysn.movies_sprint.utils.Resource
import timber.log.Timber

@SuppressLint("CustomSplashScreen")
class MovieDetailActivity : BaseActivity() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var binding: ActivityMovieDetailBinding
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setup()
        adjustUI()
        listeners()
    }

    private fun setup() {
        id = intent.getIntExtra(MOVIE_ID, 0)
        getMovieDetail(id)
    }

    private fun getMovieDetail(id: Int) {
        viewModel.getMovieDetail(id).observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    //  it.data?.let { it1 -> onPopularMoviesFetched(it1) }
                    hideLoading()
                }
                Resource.Status.ERROR -> {
                    Timber.e("onError : ${it.message}")
                    hideLoading()
                }
                Resource.Status.LOADING -> {
                    showLoading()
                }
            }
        }
    }

    private fun adjustUI() {
    }

    private fun listeners() {
    }

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        fun createSimpleIntent(context: Context, id: Int?): Intent {
            return Intent(context, MovieDetailActivity::class.java).putExtra(MOVIE_ID, id)
        }
    }
}
