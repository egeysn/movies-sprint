package com.egeysn.movies_sprint.data.remote

import com.egeysn.movies_sprint.data.response.MovieResponse
import com.egeysn.movies_sprint.data.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/multi")
    suspend fun searchMultiSections(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
    ): Response<SearchResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
    ): Response<SearchResponse>

    @GET("movie/{id}")
    suspend fun movieDetail(
        @Query("id") id: Int,
        @Query("api_key") apiKey: String,
    ): Response<MovieResponse>
}
