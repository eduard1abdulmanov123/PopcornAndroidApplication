package com.abdulmanov.core.remote.models.movies

data class CastApi(
    val cast_id: Long,
    val character: String,
    val credit_id: String,
    val gender: Int?,
    val id: Long,
    val name: String,
    val order: Int,
    val profile_path: String?
)