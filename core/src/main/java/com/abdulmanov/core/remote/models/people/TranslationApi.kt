package com.abdulmanov.core.remote.models.people

data class TranslationApi(
    val data: DataApi,
    val english_name: String,
    val iso_3166_1: String,
    val iso_639_1: String,
    val name: String
)