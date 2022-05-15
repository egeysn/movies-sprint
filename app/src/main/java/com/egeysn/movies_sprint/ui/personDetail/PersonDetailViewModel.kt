package com.egeysn.movies_sprint.ui.personDetail

import androidx.lifecycle.LiveData
import com.egeysn.movies_sprint.common.BaseViewModel
import com.egeysn.movies_sprint.data.repositories.NetworkRepository
import com.egeysn.movies_sprint.data.response.PersonDetailResponse
import com.egeysn.movies_sprint.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(val repository: NetworkRepository) : BaseViewModel() {

    fun getPersonDetail(id: Int): LiveData<Resource<PersonDetailResponse>> {
        return repository.getPersonDetail(id)
    }
}
