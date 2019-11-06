package com.abdulmanov.core.remote.models.people

data class MovieCreditsApi(
    val cast: List<MovieCastApi>,
    val crew: List<MovieCrewApi>
)