package com.egeysn.movies_sprint.ui.main

import androidx.lifecycle.LiveData
import com.egeysn.movies_sprint.common.BaseViewModel
import com.egeysn.movies_sprint.data.repositories.NetworkRepository
import com.egeysn.movies_sprint.data.response.SearchResponse
import com.egeysn.movies_sprint.utils.GeneralUtils
import com.egeysn.movies_sprint.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NetworkRepository, val utils: GeneralUtils) : BaseViewModel() {

    fun searchMultiSection(query: String): LiveData<Resource<SearchResponse>> {
        return repository.searchMultiSection(query)
    }

    fun getPopularMovies(): LiveData<Resource<SearchResponse>> {
        return repository.getPopularMovies()
    }
}
