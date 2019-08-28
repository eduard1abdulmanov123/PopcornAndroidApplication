package com.abdulmanov.core.network.dto.people

import com.google.gson.annotations.SerializedName

data class PeopleExternalIDsDTO(
    @SerializedName("imdb_id")
    val imdbId:String?,
    @SerializedName("facebook_id")
    val facebookId:String?,
    @SerializedName("freebase_mid")
    val freebaseMid:String?,
    @SerializedName("freebase_id")
    val freebaseId:String?,
    @SerializedName("tvrage_id")
    val tvrageId:Long?,
    @SerializedName("twitter_id")
    val twitterId:String?,
    @SerializedName("instagram_id")
    val instagramId:String?
)