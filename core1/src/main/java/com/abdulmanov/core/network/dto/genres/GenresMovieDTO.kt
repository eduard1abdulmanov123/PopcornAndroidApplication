package com.abdulmanov.core.network.dto.genres

import com.google.gson.annotations.SerializedName

data class GenresMovieDTO(
    @SerializedName("genres")
    val genres:List<GenreDTO>
)