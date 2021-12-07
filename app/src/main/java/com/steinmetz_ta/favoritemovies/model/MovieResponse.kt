package com.steinmetz_ta.favoritemovies.model

import java.io.Serializable

data class MovieResponse(
    val errorMessage: String,
    val expression: String,
    val results: List<Result>,
    val searchType: String
): Serializable