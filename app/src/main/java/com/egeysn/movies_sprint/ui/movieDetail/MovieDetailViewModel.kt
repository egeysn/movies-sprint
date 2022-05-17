package com.egeysn.movies_sprint.ui.movieDetail

import androidx.lifecycle.LiveData
import com.egeysn.movies_sprint.common.BaseViewModel
import com.egeysn.movies_sprint.data.repositories.NetworkRepository
import com.egeysn.movies_sprint.data.response.CreditResponse
import com.egeysn.movies_sprint.data.response.MovieResponse
import com.egeysn.movies_sprint.data.response.MovieVideosResponse
import com.egeysn.movies_sprint.utils.GeneralUtils
import com.egeysn.movies_sprint.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: NetworkRepository,) : BaseViewModel() {

    @Inject
    lateinit var utils: GeneralUtils

    fun getMovieDetail(id: Int): LiveData<Resource<MovieResponse>> {
        return repository.getMovieDetail(id)
    }

    fun getCredits(id: Int): LiveData<Resource<CreditResponse>> {
        return repository.getCredits(id)
    }

    fun getVideoTrailers(id: Int): LiveData<Resource<MovieVideosResponse>> {
        return repository.getMovieTrailers(id)
    }
}
