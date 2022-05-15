package com.egeysn.movies_sprint.data.repositories

import com.egeysn.movies_sprint.BuildConfig
import com.egeysn.movies_sprint.common.BaseDataSource
import com.egeysn.movies_sprint.data.remote.ApiService
import com.egeysn.movies_sprint.utils.performOperation
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

    fun getPopularMovies() =
        performOperation {
            getResult {
                apiService.getPopularMovies(
                    BuildConfig.API_KEY
                )
            }
        }

    fun getMovieDetail(id: Int) =
        performOperation {
            getResult {
                apiService.movieDetail(
                    id,
                    BuildConfig.API_KEY
                )
            }
        }

    fun getPersonDetail(id: Int) =
        performOperation {
            getResult {
                apiService.personDetail(
                    id,
                    BuildConfig.API_KEY
                )
            }
        }

    fun getCredits(id: Int) =
        performOperation {
            getResult {
                apiService.getCredits(
                    id,
                    BuildConfig.API_KEY
                )
            }
        }
}
