package com.abdulmanov.myapplication.model.vo

data class FilmDetails(
    val id:Long,
    val posterPath:String,
    val title:String,
    val originalTitle:String,
    val releaseData:String,
    val voteAverage:String,
    val voteCount:String,
    val countries:List<String>,
    val genres:List<String>,
    val runtime:String,
    val budget:String,
    val revenue:String,
    val overview:String,
    val credits:List<Credit>,
    val videos:List<Video>
)

data class Credit(
    val profilePath:String?,
    val name:String,
    val job:String
)

data class Video(
    val pathUrl:String,
    val thumbnail:String
)