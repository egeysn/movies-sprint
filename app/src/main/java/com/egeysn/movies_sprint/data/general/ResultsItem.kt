package com.egeysn.movies_sprint.data.general

data class ResultsItem(
    val overview: String?,
    val original_language: String?,
    val originalTitle: String?,
    val video: Boolean?,
    val original_title: String?,
    val genreIds: List<Int?>?,
    val poster_path: String?,
    val backdrop_path: String?,
    val releaseDate: String?,
    val popularity: Double?,
    val voteAverage: Double?,
    val id: Int?,
    val adult: Boolean?,
    val voteCount: Int?,
    val media_type: String?,
    val profile_path: String?
)
