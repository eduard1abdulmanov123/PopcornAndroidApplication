package com.abdulmanov.core.remote.models.movies

data class VideoApi(
    val id: String,
    val iso_3166_1: String,
    val iso_639_1: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Long,
    val type: String
)