package com.egeysn.movies_sprint.ui.movieDetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.viewModels
import com.egeysn.movies_sprint.adapters.CastItemListener
import com.egeysn.movies_sprint.adapters.CastsAdapter
import com.egeysn.movies_sprint.adapters.pagerAdapters.MovieTrailersListener
import com.egeysn.movies_sprint.adapters.pagerAdapters.MovieVideosPagerAdapter
import com.egeysn.movies_sprint.data.common.BaseActivity
import com.egeysn.movies_sprint.data.response.CreditResponse
import com.egeysn.movies_sprint.data.response.MovieResponse
import com.egeysn.movies_sprint.data.response.MovieVideosResponse
import com.egeysn.movies_sprint.databinding.ActivityMovieDetailBinding
import com.egeysn.movies_sprint.ui.personDetail.PersonDetailActivity
import com.egeysn.movies_sprint.ui.personDetail.PersonDetailActivity.Companion.MOVIE_ID
import com.egeysn.movies_sprint.utils.GlideHelper
import com.egeysn.movies_sprint.utils.Resource
import com.egeysn.movies_sprint.utils.ZoomOutPageTransformer
import com.egeysn.movies_sprint.utils.toYear
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*

@SuppressLint("CustomSplashScreen")
class MovieDetailActivity() : BaseActivity() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var binding: ActivityMovieDetailBinding
    private var id: Int = 0

    // TODO FIX AUTOMATICALLY SWIPE PROBLEM ON TRAILERS
    var currentTrailersPage = 0
    var timer: Timer? = null
    private val DELAY_MS: Long = 500 // delay in milliseconds before task is to be executed
    private val PERIOD_MS: Long = 6000 // time in milliseconds between successive task executions.
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var changeTrailersJob: Job? = null

    private var viewPagerAdapter: MovieVideosPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        id = intent.getIntExtra(MOVIE_ID, 0)
        getMovieDetail(id)
        getMovieTrailers(id)
        getCasts(id)
    }

    private fun getMovieDetail(id: Int) {
        viewModel.getMovieDetail(id).observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> adjustUI(it1) }

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

    private fun getMovieTrailers(id: Int) {
        viewModel.getVideoTrailers(id).observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    setupMovieTrailers(it)
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
                RelativeLayout.LayoutParams(posterWidth, (posterWidth * (1.5)).toInt())

            GlideHelper.loadImage(this, response.poster_path, binding.posterIv)

            val genresNameList = response.genres.map { it?.name }.toList()
            val genresString = genresNameList.joinToString(",")
            binding.apply {
                titleTv.text = response.original_title
                genresTv.text = genresString
                releaseDateTv.text = "${response.release_date?.toYear()}"
                descTv.text = response.overview
                ratingBar.rating = (response.vote_average ?: 0).toFloat() / 2f
            }
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
            binding.castContainer.visibility = View.GONE
        } else {
            binding.castContainer.visibility = View.VISIBLE
            val adapter = CastsAdapter(
                this, response.cast,
                object : CastItemListener {
                    override fun onItemClicked(id: Int?) {
                        startActivity(
                            PersonDetailActivity.createSimpleIntent(
                                this@MovieDetailActivity,
                                id
                            )
                        )
                    }
                }
            )
            binding.castRv.adapter = adapter
        }
    }

    private fun setupMovieTrailers(response: Resource<MovieVideosResponse>) {
        val trailerList = response.data?.results
        if (!trailerList.isNullOrEmpty()) {
            viewPagerAdapter = MovieVideosPagerAdapter(viewModel, trailerList)
            viewPagerAdapter?.setListener(object : MovieTrailersListener {
                override fun onItemClicked(youTubePlayer: YouTubePlayerView) {
                    // stopViewPagerSwipe()
                }
            })
            binding.apply {
                videoTrailersPager.visibility = View.VISIBLE
                videoTrailersPager.adapter = viewPagerAdapter
                videoTrailersPager.setPageTransformer(ZoomOutPageTransformer())
                videoTrailersPager.setCurrentItem(0, false)
                // startViewPagerTimer()
            }
        } else {
            binding.videoTrailersPager.visibility = View.GONE
        }
    }

/*    private fun changeTrailersAutomatically() {
        changeTrailersJob?.cancel()
        changeTrailersJob = coroutineScope.launch {
            if (currentTrailersPage == viewPagerAdapter?.itemCount) {
                currentTrailersPage = 0
            }
            binding.videoTrailersPager.setCurrentItem(currentTrailersPage++, true)
        }
    }

    private fun startViewPagerTimer() {

        timer = Timer() // This will create a new Thread
        timer!!.schedule(
            object : TimerTask() {
                // task to be scheduled
                override fun run() {
                    changeTrailersAutomatically()
                }
            },
            DELAY_MS, PERIOD_MS
        )
    }

    private fun stopViewPagerSwipe() {
        timer?.cancel()
        changeTrailersJob?.cancel()
    }*/

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        fun createSimpleIntent(context: Context, id: Int?): Intent {
            return Intent(context, MovieDetailActivity::class.java).putExtra(MOVIE_ID, id)
        }
    }
}
