package com.abdulmanov.core.network.dto.people

import com.google.gson.annotations.SerializedName

data class PeopleMovieCastDTO(
    @SerializedName("character")
    val character:String?,
    @SerializedName("credit_id")
    val creditId:String,
    @SerializedName("release_date")
    val releaseData:String?,
    @SerializedName("vote_count")
    val voteCount:Long,
    @SerializedName("video")
    val video:Boolean,
    @SerializedName("adult")
    val adult:Boolean,
    @SerializedName("vote_average")
    val voteAverage:Double,
    @SerializedName("title")
    val title: String,
    @SerializedName("genre_ids")
    val genres: List<String>,
    @SerializedName("original_language")
    val originalLanguage:String,
    @SerializedName("original_title")
    val originalTitle:String,
    @SerializedName("id")
    val id:Long,
    @SerializedName("backdrop_path")
    val backdropPath:String?,
    @SerializedName("overview")
    val overview:String,
    @SerializedName("poster_path")
    val posterPath:String?
)