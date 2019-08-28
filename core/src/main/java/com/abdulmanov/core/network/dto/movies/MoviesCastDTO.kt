package com.abdulmanov.core.network.dto.movies

import com.google.gson.annotations.SerializedName

data class MoviesCastDTO(
    @SerializedName("cast_id")
    val castId:Int,
    @SerializedName("character")
    val character:String,
    @SerializedName("credit_id")
    val creditId:String,
    @SerializedName("gender")
    val gender:Int?,
    @SerializedName("id")
    val id:Long,
    @SerializedName("order")
    val order:Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("profile_path")
    val profilePath:String?
)