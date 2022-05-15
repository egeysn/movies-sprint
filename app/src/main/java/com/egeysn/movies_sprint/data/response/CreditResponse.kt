package com.egeysn.movies_sprint.data.response

import com.egeysn.movies_sprint.data.general.CastItem
import com.egeysn.movies_sprint.data.general.CrewItem

data class CreditResponse(
    val cast: List<CastItem>?,
    val id: Int? = null,
    val crew: List<CrewItem?>?
)
