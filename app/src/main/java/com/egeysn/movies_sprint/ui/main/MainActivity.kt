package com.egeysn.movies_sprint.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.egeysn.movies_sprint.adapters.ParentAdapter
import com.egeysn.movies_sprint.data.common.BaseActivity
import com.egeysn.movies_sprint.data.general.ParentModel
import com.egeysn.movies_sprint.data.general.ResultsItem
import com.egeysn.movies_sprint.data.response.SearchResponse
import com.egeysn.movies_sprint.databinding.ActivityMainBinding
import com.egeysn.movies_sprint.utils.Resource
import timber.log.Timber

@SuppressLint("CustomSplashScreen")
class MainActivity : BaseActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adjustUI()

        searchMultiSections()
    }

    private fun adjustUI() {
    }

    private fun searchMultiSections() {
        viewModel.searchMultiSection("emma").observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> onGamesFetched(it1) }
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

    private fun onGamesFetched(response: SearchResponse) {
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

    companion object {
        fun createSimpleIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
