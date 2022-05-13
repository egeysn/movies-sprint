package com.egeysn.movies_sprint.data.response

import com.egeysn.movies_sprint.data.general.ResultsItem

data class SearchResponse(
    val page: Int? = null,
    val totalPages: Int? = null,
    val results: List<ResultsItem?>? = null,
    val totalResults: Int? = null
)
