package com.abdulmanov.core.remote.models.movies

data class CrewApi(
    val credit_id: String,
    val department: String,
    val gender: Int?,
    val id: Long,
    val job: String,
    val name: String,
    val profile_path: String?
)