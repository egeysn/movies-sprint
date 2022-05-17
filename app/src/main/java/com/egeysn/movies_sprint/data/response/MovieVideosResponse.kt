package com.egeysn.movies_sprint.data.response

import com.egeysn.movies_sprint.data.general.MovieVideosItem
import com.google.gson.annotations.SerializedName

data class MovieVideosResponse(

    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("results")
    val results: List<MovieVideosItem?>?
)
