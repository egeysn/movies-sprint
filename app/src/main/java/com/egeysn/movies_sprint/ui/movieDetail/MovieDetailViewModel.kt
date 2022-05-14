package com.egeysn.movies_sprint.ui.movieDetail

import androidx.lifecycle.LiveData
import com.egeysn.movies_sprint.common.BaseViewModel
import com.egeysn.movies_sprint.data.repositories.NetworkRepository
import com.egeysn.movies_sprint.data.response.MovieResponse
import com.egeysn.movies_sprint.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(val repository: NetworkRepository) : BaseViewModel() {

    fun getMovieDetail(id: Int): LiveData<Resource<MovieResponse>> {
        return repository.getMovieDetail(id)
    }
}
