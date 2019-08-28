package com.abdulmanov.MoviCorn.model.vo.movie

data class FilmLittle(
    val id:Long,
    val title:String,
    val voteAverage:String,
    val releaseDate:String?,
    val posterPath:String?
)