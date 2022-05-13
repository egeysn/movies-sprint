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
import com.egeysn.movies_sprint.data.common.BaseActivity
import com.egeysn.movies_sprint.data.general.ParentModel
import com.egeysn.movies_sprint.data.general.ResultsItem
import com.egeysn.movies_sprint.data.response.SearchResponse
import com.egeysn.movies_sprint.databinding.ActivityMainBinding
import com.egeysn.movies_sprint.utils.Resource
import kotlinx.coroutines.*
import timber.log.Timber

@SuppressLint("CustomSplashScreen")
class MainActivity : BaseActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
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
        searchMultiSections("")
    }

    private fun adjustUI() {
    }

    private fun searchMultiSections(query: String) {
        viewModel.searchMultiSection(query).observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> onMoviesFetched(it1) }
                    hideLoading()
                }
                Resource.Status.ERROR -> {
                    Timber.e("onError : ${it.message}")
                    binding.body.visibility = View.GONE
                    binding.emptyList.visibility = View.VISIBLE
                    hideLoading()
                }
                Resource.Status.LOADING -> {

                    showLoading()
                }
            }
        }
    }

    private fun onMoviesFetched(response: SearchResponse) {
        Timber.d("games list fetched")
        val resultList = response.results
        val groupList =
            resultList?.groupBy { it?.media_type }
                ?.map { ParentModel(type = it.key, results = it.value as List<ResultsItem>) }

        if (resultList.isNullOrEmpty()) {
            binding.body.visibility = View.GONE
            binding.emptyList.visibility = View.VISIBLE
        } else {
            binding.body.visibility = View.VISIBLE
            binding.emptyList.visibility = View.GONE
            val adapter = groupList?.let { ParentAdapter(this, it) }
            binding.gamesRv.adapter = adapter
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
                        delay(1000)
                        if (it.length > 1) {
                            searchMultiSections(input)
                        } else {
                            // viewModel.resetSearch()
                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun destroy() {
        searchJob?.cancel()
    }

    companion object {
        fun createSimpleIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
