package com.egeysn.movies_sprint.ui.main

import androidx.lifecycle.LiveData
import com.egeysn.movies_sprint.common.BaseViewModel
import com.egeysn.movies_sprint.data.repositories.NetworkRepository
import com.egeysn.movies_sprint.data.response.SearchResponse
import com.egeysn.movies_sprint.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(val repository: NetworkRepository) : BaseViewModel() {

    fun searchMultiSection(query: String): LiveData<Resource<SearchResponse>> {
        return repository.searchMultiSection(query)
    }
}
