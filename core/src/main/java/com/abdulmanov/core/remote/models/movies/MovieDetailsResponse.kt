package com.abdulmanov.core.remote.models.movies

import com.abdulmanov.core.remote.models.genres.GenreMovieApi

data class MovieDetailsResponse(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: Any?,
    val budget: Long,
    val genres: List<GenreMovieApi>,
    val homepage: String?,
    val id: Long,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val production_companies: List<ProductionCompanyApi>,
    val production_countries: List<ProductionCountryApi>,
    val release_date: String,
    val revenue: Long,
    val runtime: Int?,
    val spoken_languageApis: List<SpokenLanguageApi>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Long,
    val videos:ResultVideosApi,
    val similar:MoviesResponse,
    val credits:CreditsApi
)