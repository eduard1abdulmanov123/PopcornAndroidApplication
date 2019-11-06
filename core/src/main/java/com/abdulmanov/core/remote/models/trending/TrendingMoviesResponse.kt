package com.abdulmanov.core.remote.models.trending

data class TrendingMoviesResponse(
    val page: Int,
    val results: List<ResultApi>,
    val total_pages: Int,
    val total_results: Int
)