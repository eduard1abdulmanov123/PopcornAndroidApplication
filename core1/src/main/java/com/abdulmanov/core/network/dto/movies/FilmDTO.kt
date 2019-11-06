package com.abdulmanov.core.network.dto.movies

import com.google.gson.annotations.SerializedName

data class FilmDTO(
    @SerializedName("adult")
    val adult:Boolean,
    @SerializedName("backdrop_path")
    val backdropPath:String?,
    @SerializedName("genre_ids")
    val genres: List<String>,
    @SerializedName("id")
    val id: Long,
    @SerializedName("original_language")
    val originalLanguage:String,
    @SerializedName("original_title")
    val originalTitle:String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_count")
    val voteCount: Long,
    @SerializedName("vote_average")
    val voteAverage: Double
)