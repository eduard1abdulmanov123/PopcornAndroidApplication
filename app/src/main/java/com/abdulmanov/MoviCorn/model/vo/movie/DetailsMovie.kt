package com.abdulmanov.MoviCorn.model.vo.movie

data class DetailsMovie(
    val id:Long,
    val posterPath:String?,
    val title:String,
    val originalTitle:String,
    val releaseData:String,
    val voteAverage:Double,
    val voteCount:Long,
    val countries:List<String>,
    val genres:List<String>,
    val runtime:String?,
    val budget:String,
    val revenue:String,
    val overview:String?,
    val movieDbID:String,
    val movieCredits:List<MovieCredit>,
    val movieVideos:List<MovieVideo>,
    val similar: List<FilmLittle>,
    var existsInTheDB:Boolean = false
)
