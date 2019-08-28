package com.abdulmanov.core.network.dto.people

import com.google.gson.annotations.SerializedName

data class PeopleTVCrewDTO(
    @SerializedName("department")
    val department:String,
    @SerializedName("job")
    val job:String,
    @SerializedName("original_name")
    val originalName:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("episode_count")
    val episodeCount:Int,
    @SerializedName("first_air_date")
    val firstAirData:String,
    @SerializedName("origin_country")
    val originCountry:List<String>,
    @SerializedName("credit_id")
    val creditId:String,
    @SerializedName("vote_count")
    val voteCount:Long,
    @SerializedName("vote_average")
    val voteAverage:Double,
    @SerializedName("genre_ids")
    val genres: List<String>,
    @SerializedName("id")
    val id:Long,
    @SerializedName("backdrop_path")
    val backdropPath:String?,
    @SerializedName("overview")
    val overview:String,
    @SerializedName("poster_path")
    val posterPath:String?
)