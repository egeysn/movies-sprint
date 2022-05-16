package com.egeysn.movies_sprint.ui.movieDetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import com.egeysn.movies_sprint.adapters.CastItemListener
import com.egeysn.movies_sprint.adapters.CastsAdapter
import com.egeysn.movies_sprint.data.common.BaseActivity
import com.egeysn.movies_sprint.data.response.CreditResponse
import com.egeysn.movies_sprint.data.response.MovieResponse
import com.egeysn.movies_sprint.databinding.ActivityMovieDetailBinding
import com.egeysn.movies_sprint.ui.personDetail.PersonDetailActivity
import com.egeysn.movies_sprint.utils.GlideHelper
import com.egeysn.movies_sprint.utils.Resource
import com.egeysn.movies_sprint.utils.toYear
import timber.log.Timber

@SuppressLint("CustomSplashScreen")
class MovieDetailActivity() : BaseActivity() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var binding: ActivityMovieDetailBinding
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        id = intent.getIntExtra(MOVIE_ID, 0)
        getMovieDetail(id)
    }

    private fun getMovieDetail(id: Int) {
        viewModel.getMovieDetail(id).observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> adjustUI(it1) }
                    getCasts(id)
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

    private fun adjustUI(response: MovieResponse?) {
        if (response == null) {
            binding.emptyBody.visibility = View.VISIBLE
            binding.body.visibility = View.GONE
        } else {
            binding.emptyBody.visibility = View.GONE
            binding.body.visibility = View.VISIBLE

            val posterWidth = utils.dpToPx(110f)
            binding.posterIv.layoutParams =
                LinearLayout.LayoutParams(posterWidth, (posterWidth * (1.5)).toInt())

            GlideHelper.loadImage(this, response.poster_path, binding.posterIv)

            val genresNameList = response.genres.map { it?.name }.toList()
            val genresString = genresNameList.joinToString(",")
            binding.genresTv.text = genresString
            binding.releaseDateTv.text = "${response.release_date?.toYear()}"
            binding.descTv.text = response.overview
            binding.ratingBar.rating = (response.vote_average ?: 0).toFloat() / 2f
        }
    }
    private fun getCasts(id: Int) {
        viewModel.getCredits(id).observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> onCreditsFetched(it1) }
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

    private fun onCreditsFetched(response: CreditResponse) {
        if (response.cast.isNullOrEmpty()) {
            binding.castContainer.visibility = View.GONE } else {
            binding.castContainer.visibility = View.VISIBLE
            val adapter = CastsAdapter(
                this, response.cast,
                object : CastItemListener {
                    override fun onItemClicked(id: Int?) {
                        startActivity(PersonDetailActivity.createSimpleIntent(this@MovieDetailActivity,id))
                    }
                }
            )
            binding.castRv.adapter = adapter
        }
    }

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        fun createSimpleIntent(context: Context, id: Int?): Intent {
            return Intent(context, MovieDetailActivity::class.java).putExtra(MOVIE_ID, id)
        }
    }
}
