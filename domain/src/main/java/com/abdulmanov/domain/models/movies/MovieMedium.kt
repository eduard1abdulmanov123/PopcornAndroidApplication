package com.abdulmanov.domain.models.movies

data class MovieMedium(
    val id: Long,
    val voteAverage: String,
    val voteCount: String,
    val title: String,
    val posterPath: String?,
    val overview: String,
    val releaseDate: String?,
    val genres: List<String>
)