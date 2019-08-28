package com.abdulmanov.core.network.dto.movies

import com.google.gson.annotations.SerializedName


data class MoviesDTO(
    @SerializedName("page")
    val page:Int,
    @SerializedName("results")
    val results: List<FilmDTO>,
    @SerializedName("total_results")
    val totalResults:Int,
    @SerializedName("total_pages")
    val totalPages:Int
)


