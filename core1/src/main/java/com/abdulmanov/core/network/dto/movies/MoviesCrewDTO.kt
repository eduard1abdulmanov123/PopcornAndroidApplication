package com.abdulmanov.core.network.dto.movies

import com.google.gson.annotations.SerializedName

data class MoviesCrewDTO(
    @SerializedName("credit_id")
    val creditId: String,
    @SerializedName("department")
    val department: String,
    @SerializedName("gender")
    val gender:Int?,
    @SerializedName("id")
    val id:Long,
    @SerializedName("job")
    val job:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("profile_path")
    val profilePath:String?
)