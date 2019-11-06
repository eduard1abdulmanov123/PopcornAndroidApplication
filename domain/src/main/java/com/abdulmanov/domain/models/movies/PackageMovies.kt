package com.abdulmanov.domain.models.movies

data class PackageMovies(
    val recommendations:List<MovieShort>,
    val nowPlaying:List<MovieShort>,
    val upcoming:List<MovieShort>,
    val popular:List<MovieShort>,
    val topRated:List<MovieShort>
)