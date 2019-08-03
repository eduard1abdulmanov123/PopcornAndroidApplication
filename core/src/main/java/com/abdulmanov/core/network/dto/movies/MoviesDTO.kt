package com.abdulmanov.core.network.dto.movies

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class MoviesDTO(
    @SerializedName("page")
    @Expose
    val page:Int,
    @SerializedName("results")
    @Expose
    val results: List<FilmDTO>,
    @SerializedName("total_results")
    @Expose
    val totalResults:Int,
    @SerializedName("total_pages")
    @Expose
    val totalPages:Int
)

data class FilmDTO(
    @SerializedName("poster_path")
    @Expose
    val posterPath: String?,
    @SerializedName("overview")
    @Expose
    val overview: String,
    @SerializedName("release_date")
    @Expose
    val releaseDate: String,
    @SerializedName("genre_ids")
    @Expose
    val genres: List<String>,
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double
)

