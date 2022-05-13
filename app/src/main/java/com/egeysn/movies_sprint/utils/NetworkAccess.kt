package com.egeysn.movies_sprint.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.egeysn.movies_sprint.utils.Resource
import kotlinx.coroutines.Dispatchers

fun <T> performOperation(networkCall: suspend () -> Resource<T>): LiveData<Resource<T>> =
    liveData(
        Dispatchers.IO
    ) {
        emit(Resource.loading())
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            emit(Resource.success(responseStatus.data!!))
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }