package com.abdulmanov.core.remote.models.people

data class PeopleDetailsResponse(
    val adult: Boolean,
    val also_known_as: List<String>,
    val biography: String,
    val birthday: String?,
    val deathday: String?,
    val external_ids: ExternalIdsApi,
    val gender: Int,
    val homepage: String?,
    val id: Long,
    val imdb_id: String,
    val known_for_department: String,
    val movie_credits: MovieCreditsApi,
    val name: String,
    val place_of_birth: String?,
    val popularity: Double,
    val profile_path: String?,
    val translations: TranslationsApi,
    val tv_credits: TvCreditsApi
)