package com.abdulmanov.core.network.dto.movies
import com.abdulmanov.core.network.dto.genres.GenreDTO
import com.google.gson.annotations.SerializedName


data class MoviesDetailsDTO(
    @SerializedName("adult")
    val adult:Boolean,
    @SerializedName("backdrop_path")
    val backdrop_path:String?,
    @SerializedName("budget")
    val budget:Long,
    @SerializedName("genres")
    val genres:List<GenreDTO>,
    @SerializedName("homepage")
    val homepage:String?,
    @SerializedName("id")
    val id:Long,
    @SerializedName("imdb_id")
    val imdbId:String?,
    @SerializedName("original_language")
    val originalLanguage:String,
    @SerializedName("original_title")
    val originalTitle:String,
    @SerializedName("overview")
    val overview:String?,
    @SerializedName("poster_path")
    val posterPath:String?,
    @SerializedName("production_countries")
    val productionCountries:List<ProductionCountryDTO>,
    @SerializedName("release_date")
    val releaseDate:String,
    @SerializedName("revenue")
    val revenue:Long,
    @SerializedName("runtime")
    val runtime:Int?,
    @SerializedName("title")
    val title:String,
    @SerializedName("vote_average")
    val voteAverage:Double,
    @SerializedName("vote_count")
    val voteCount:Long,
    @SerializedName("videos")
    val videos: ListVideosDTO,
    @SerializedName("credits")
    val credits: ListCreditsDTO,
    @SerializedName("similar")
    val similar: MoviesDTO
)

/*Video*/
data class ListVideosDTO(
    @SerializedName("results")
    val results:List<MoviesVideoDTO>
)

/*Credits*/
data class ListCreditsDTO(
    @SerializedName("cast")
    val moviesCast:List<MoviesCastDTO>,
    @SerializedName("crew")
    val moviesCrew:List<MoviesCrewDTO>
)


