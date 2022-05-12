package com.egeysn.video_games_sprint.data.repositories

import com.egeysn.video_games_sprint.data.remote.ApiService
import com.naylalabs.kotlinbaseproject.common.BaseDataSource
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val apiService: ApiService
) : BaseDataSource()
