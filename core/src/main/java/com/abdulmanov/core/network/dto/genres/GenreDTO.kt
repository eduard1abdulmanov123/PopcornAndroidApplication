package com.abdulmanov.core.network.dto.genres

import com.google.gson.annotations.SerializedName

data class GenreDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String
)