package com.abdulmanov.core.network.dto.movies

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class GenresDTO(
    @SerializedName("genres")
    @Expose
    val genres:List<GenreDTO>
)

data class GenreDTO(
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("name")
    @Expose
    val name: String
)
