package com.egeysn.movies_sprint.data.response

import com.egeysn.movies_sprint.data.general.*

data class MovieResponse(
    val originalLanguage: String,
    val imdbId: String,
    val video: Boolean,
    val title: String,
    val backdropPath: String,
    val revenue: Int,
    val genres: List<GenresItem?>,
    val popularity: Double,
    val productionCountries: List<ProductionCountriesItem?>,
    val id: Int,
    val voteCount: Int,
    val budget: Int,
    val overview: String,
    val originalTitle: String,
    val runtime: Int,
    val posterPath: String,
    val spokenLanguages: List<SpokenLanguagesItem?>,
    val productionCompanies: List<ProductionCompaniesItem?>,
    val releaseDate: String,
    val voteAverage: Double,
    val belongsToCollection: BelongsToCollection,
    val tagline: String,
    val adult: Boolean,
    val homepage: String,
    val status: String
)
