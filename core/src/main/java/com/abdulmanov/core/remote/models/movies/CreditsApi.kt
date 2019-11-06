package com.abdulmanov.core.remote.models.movies

data class CreditsApi(
    val cast: List<CastApi>,
    val crew: List<CrewApi>
)