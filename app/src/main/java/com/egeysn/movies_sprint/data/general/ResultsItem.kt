package com.egeysn.movies_sprint.data.general

data class ResultsItem(
    val overview: String?,
    val original_language: String?,
    val video: Boolean?,
    val original_title: String?,
    val genreIds: List<Int?>?,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String?,
    val popularity: Double?,
    val vote_average: Double?,
    val id: Int?,
    val adult: Boolean?,
    val voteCount: Int?,
    val media_type: String?,
    val profile_path: String?,
    val known_for_department: String?,
    val name: String?
)
