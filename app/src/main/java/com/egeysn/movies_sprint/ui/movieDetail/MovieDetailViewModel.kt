package com.egeysn.movies_sprint.ui.movieDetail

import com.egeysn.movies_sprint.common.BaseViewModel
import com.egeysn.movies_sprint.data.repositories.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(val repository: NetworkRepository) : BaseViewModel() {

}
