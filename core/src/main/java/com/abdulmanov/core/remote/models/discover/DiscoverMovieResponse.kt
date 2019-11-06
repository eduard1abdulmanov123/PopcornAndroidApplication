package com.abdulmanov.core.remote.models.discover

data class DiscoverMovieResponse(
    val page: Int,
    val results: List<ResultApi>,
    val total_pages: Int,
    val total_results: Int
)