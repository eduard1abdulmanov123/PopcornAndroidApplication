package com.abdulmanov.domain.models.movies

data class MovieDetails(
    val id:Long,
    val posterPath:String?,
    val title:String,
    val originalTitle:String,
    val releaseData:String,
    val voteAverage:Double,
    val voteCount:Long,
    val countries:List<String>,
    val genres:List<String>,
    val runtime:Int?,
    val budget:String,
    val revenue:String,
    val overview:String?,
    val tmdbUrl:String,
    val credits:List<Credit>,
    val videos:List<Video>,
    val similar:List<MovieShort>,
    var inLocalDb:Boolean = false
)