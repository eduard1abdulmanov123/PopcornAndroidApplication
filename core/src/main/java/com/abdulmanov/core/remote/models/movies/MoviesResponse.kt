package com.abdulmanov.core.remote.models.movies

data class MoviesResponse(
    val dates: DatesApi,
    val page: Int,
    val results: List<ResultApi>,
    val total_pages: Int,
    val total_results: Int
)