package com.abdulmanov.domain.models.movies

data class MovieShort(
    val id:Long,
    val title:String,
    val voteAverage:String,
    val releaseDate:String?,
    val posterPath:String?,
    val backdropPath:String?
)