package com.abdulmanov.core.network.dto.people

import com.google.gson.annotations.SerializedName

data class TranslationBiographyDTO(
    @SerializedName("iso_639_1")
    val iso639_1:String,
    @SerializedName("iso_3166_1")
    val iso3166_1:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("data")
    val data:DataDTO,
    @SerializedName("english_name")
    val englishName:String
)

data class DataDTO(
    @SerializedName("biography")
    val biography:String
)