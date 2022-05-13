package com.egeysn.movies_sprint.data.repositories

import com.egeysn.movies_sprint.BuildConfig
import com.egeysn.movies_sprint.data.remote.ApiService
import com.egeysn.movies_sprint.utils.performOperation
import com.egeysn.movies_sprint.common.BaseDataSource
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val apiService: ApiService
) : BaseDataSource() {

    fun searchMultiSection(query: String) =
        performOperation {
            getResult {
                apiService.searchMultiSections(
                    BuildConfig.API_KEY,
                    query
                )
            }
        }
}
