package com.abdulmanov.core.remote.models.movies

data class ProductionCompanyApi(
    val id: Long,
    val logo_path: String?,
    val name: String,
    val origin_country: String
)