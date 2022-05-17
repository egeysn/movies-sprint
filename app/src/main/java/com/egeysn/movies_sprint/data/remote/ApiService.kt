package com.egeysn.movies_sprint.data.remote

import com.egeysn.movies_sprint.data.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): Response<MovieResponse>

    @GET("person/{id}")
    suspend fun personDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): Response<PersonDetailResponse>

    @GET("movie/{id}/credits")
    suspend fun getCredits(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): Response<CreditResponse>

    @GET("movie/{id}/videos")
    suspend fun getMovieTrailers(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
    ): Response<MovieVideosResponse>
}
