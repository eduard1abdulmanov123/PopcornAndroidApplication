package com.abdulmanov.core.remote.models.people

data class TvCreditsApi(
    val cast: List<TvCastApi>,
    val crew: List<TvCrewApi>
)