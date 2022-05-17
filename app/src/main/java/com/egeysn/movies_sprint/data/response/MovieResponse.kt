package com.egeysn.movies_sprint.data.response

import com.egeysn.movies_sprint.data.general.*

data class MovieResponse(
    val originalLanguage: String,
    val imdbId: String?,
    val video: Boolean?,
    val title: String?,
    val backdrop_path: String?,
    val revenue: Int?,
    val genres: List<GenresItem?>,
    val popularity: Double?,
    val productionCountries: List<ProductionCountriesItem?>?,
    val id: Int?,
    val voteCount: Int?,
    val budget: Int?,
    val overview: String?,
    val original_title: String?,
    val runtime: Int?,
    val poster_path: String?,
    val spokenLanguages: List<SpokenLanguagesItem?>?,
    val productionCompanies: List<ProductionCompaniesItem?>?,
    val release_date: String?,
    val vote_average: Double?,
    val belongsToCollection: BelongsToCollection?,
    val tagline: String?,
    val adult: Boolean?,
    val homepage: String?,
    val status: String?
)
