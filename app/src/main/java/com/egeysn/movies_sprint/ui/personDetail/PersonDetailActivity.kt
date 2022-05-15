package com.egeysn.movies_sprint.ui.personDetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.egeysn.movies_sprint.BuildConfig
import com.egeysn.movies_sprint.R
import com.egeysn.movies_sprint.data.common.BaseActivity
import com.egeysn.movies_sprint.data.response.MovieResponse
import com.egeysn.movies_sprint.data.response.PersonDetailResponse
import com.egeysn.movies_sprint.databinding.ActivityPersonDetailBinding
import com.egeysn.movies_sprint.utils.Resource
import com.egeysn.movies_sprint.utils.toYear
import timber.log.Timber

@SuppressLint("CustomSplashScreen")
class PersonDetailActivity() : BaseActivity() {

    private val viewModel: PersonDetailViewModel by viewModels()
    private lateinit var binding: ActivityPersonDetailBinding
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setup()
        listeners()
    }

    private fun setup() {
        id = intent.getIntExtra(MOVIE_ID, 0)
        getMovieDetail(id)
    }

    private fun getMovieDetail(id: Int) {
        viewModel.getPersonDetail(id).observe(this) {
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

    private fun adjustUI(response: PersonDetailResponse?) {
        if (response == null) {
            binding.emptyBody.visibility = View.VISIBLE
            binding.body.visibility = View.GONE
        } else {
            binding.emptyBody.visibility = View.GONE
            binding.body.visibility = View.VISIBLE

            val posterWidth = utils.screenWidth()
            binding.posterIv.layoutParams =
                LinearLayout.LayoutParams(posterWidth, (posterWidth * (1.3)).toInt())
            // create a ProgressDrawable object which we will show as placeholder
            val progress = CircularProgressDrawable(binding.root.context)
            progress.setColorSchemeColors(
                R.color.red_accent,
            )
            progress.centerRadius = 30f
            progress.strokeWidth = 5f
            progress.start()

            Glide.with(this)
                .load(BuildConfig.BASE_IMAGE_URL + response.profile_path)
                .placeholder(progress)
                .centerCrop()
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(binding.posterIv)

            binding.infoTv.text = response.known_for_department
            binding.descTv.text = response.biography ?: "Not found a biography"
        }
    }
    private fun listeners() {
    }

    companion object {
        const val MOVIE_ID = "MOVIE_ID"
        fun createSimpleIntent(context: Context, id: Int?): Intent {
            return Intent(context, PersonDetailActivity::class.java).putExtra(MOVIE_ID, id)
        }
    }
}
