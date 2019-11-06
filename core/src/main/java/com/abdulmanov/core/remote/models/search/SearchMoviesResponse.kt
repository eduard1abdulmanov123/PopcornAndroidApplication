package com.abdulmanov.core.remote.models.search

data class SearchMoviesResponse(
    val page: Int,
    val results: List<ResultApi>,
    val total_pages: Int,
    val total_results: Int
)