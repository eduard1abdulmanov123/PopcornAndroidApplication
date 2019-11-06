package com.abdulmanov.core.network.dto.movies

import com.google.gson.annotations.SerializedName

data class ProductionCountryDTO(
    @SerializedName("iso_3166_1")
    val iso:String,
    @SerializedName("name")
    val name:String
)