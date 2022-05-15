package com.egeysn.movies_sprint.data.response

data class PersonDetailResponse(
    val alsoKnownAs: List<String?>?,
    val birthday: Any?,
    val gender: Int?,
    val imdbId: Any?,
    val known_for_department: String?,
    val profile_path: Any?,
    val biography: String?,
    val deathday: Any?,
    val placeOfBirth: Any?,
    val popularity: Double?,
    val name: String?,
    val id: Int?,
    val adult: Boolean?,
    val homepage: Any?
)
