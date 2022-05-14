package com.egeysn.movies_sprint.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import com.egeysn.movies_sprint.adapters.ParentAdapter
import com.egeysn.movies_sprint.adapters.PopularMoviesAdapter
import com.egeysn.movies_sprint.adapters.PopularMoviesItemListener
import com.egeysn.movies_sprint.data.common.BaseActivity
import com.egeysn.movies_sprint.data.general.ParentModel
import com.egeysn.movies_sprint.data.general.ResultsItem
import com.egeysn.movies_sprint.data.response.SearchResponse
import com.egeysn.movies_sprint.databinding.ActivityMainBinding
import com.egeysn.movies_sprint.ui.movieDetail.MovieDetailActivity
import com.egeysn.movies_sprint.utils.Resource
import kotlinx.coroutines.*
import timber.log.Timber

@SuppressLint("CustomSplashScreen")
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    var debouncePeriod: Long = 1000

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adjustUI()
        listeners()
        getPopularMovies()
    }

    private fun adjustUI() {
    }

    private fun searchMultiSections(query: String) {
        viewModel.searchMultiSection(query).observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> onSearchMoviesFetched(it1) }
                    hideLoading()
                }
                Resource.Status.ERROR -> {
                    Timber.e("onError : ${it.message}")
                    binding.body.visibility = View.GONE
                    binding.popularMoviesBody.visibility = View.GONE
                    binding.emptyList.visibility = View.VISIBLE
                    hideLoading()
                }
                Resource.Status.LOADING -> {

                    showLoading()
                }
            }
        }
    }

    private fun getPopularMovies() {
        viewModel.getPopularMovies().observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> onPopularMoviesFetched(it1) }
                    hideLoading()
                }
                Resource.Status.ERROR -> {
                    Timber.e("onError : ${it.message}")
                    binding.body.visibility = View.GONE
                    binding.emptyList.visibility = View.VISIBLE
                    binding.popularMoviesBody.visibility = View.GONE
                    hideLoading()
                }
                Resource.Status.LOADING -> {
                    showLoading()
                }
            }
        }
    }

    private fun onPopularMoviesFetched(response: SearchResponse) {
        Timber.d("games list fetched")
        val resultList = response.results

        if (resultList.isNullOrEmpty()) {
            binding.apply {
                body.visibility = View.GONE
                emptyList.visibility = View.VISIBLE
                popularMoviesBody.visibility = View.GONE
            }
        } else {
            binding.apply {
                body.visibility = View.GONE
                emptyList.visibility = View.GONE
                popularMoviesBody.visibility = View.VISIBLE
                val adapter =
                    PopularMoviesAdapter(
                        this@MainActivity, viewModel, resultList as List<ResultsItem>,
                        object : PopularMoviesItemListener {
                            override fun onItemClicked(id: Int?) {
                                MovieDetailActivity.createSimpleIntent(this@MainActivity,id!!)
                            }
                        }
                    )
                popularMoviesRv.adapter = adapter
            }
        }
    }

    private fun onSearchMoviesFetched(response: SearchResponse) {
        Timber.d("games list fetched")
        val resultList = response.results

        if (resultList.isNullOrEmpty()) {
            binding.apply {
                body.visibility = View.GONE
                emptyList.visibility = View.VISIBLE
                popularMoviesBody.visibility = View.GONE
            }
        } else {
            val groupList =
                resultList.groupBy { it?.media_type }
                    .map { ParentModel(type = it.key, results = it.value as List<ResultsItem>) }

            binding.apply {
                body.visibility = View.VISIBLE
                binding.popularMoviesBody.visibility = View.GONE
                emptyList.visibility = View.GONE
                val adapter = ParentAdapter(this@MainActivity, groupList)
                bodyRv.adapter = adapter
            }
        }
    }

    private fun listeners() {
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val input = p0.toString()

                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    input.let {
                        delay(debouncePeriod)
                        if (it.length > 1) {
                            searchMultiSections(input)
                        } else {
                            getPopularMovies()
                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    override fun onDestroy() {
        searchJob?.cancel()
        super.onDestroy()
    }

    companion object {
        fun createSimpleIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
