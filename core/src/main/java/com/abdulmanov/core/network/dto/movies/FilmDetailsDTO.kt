package com.abdulmanov.core.network.dto.movies

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class FilmDetailsDTO(
    @SerializedName("budget")
    @Expose
    val budget:Long,
    @SerializedName("genres")
    @Expose
    val genres:List<GenreDTO>,
    @SerializedName("id")
    @Expose
    val id:Long,
    @SerializedName("original_title")
    @Expose
    val originalTitle:String,
    @SerializedName("overview")
    @Expose
    val overview:String,
    @SerializedName("poster_path")
    @Expose
    val posterPath:String?,
    @SerializedName("production_countries")
    @Expose
    val productionCountries:List<ProductionCountryDTO>,
    @SerializedName("release_date")
    @Expose
    val releaseDate:String,
    @SerializedName("revenue")
    @Expose
    val revenue:Long,
    @SerializedName("runtime")
    @Expose
    val runtime:Int,
    @SerializedName("title")
    @Expose
    val title:String,
    @SerializedName("vote_average")
    @Expose
    val voteAverage:Double,
    @SerializedName("vote_count")
    @Expose
    val voteCount:Long,
    @SerializedName("videos")
    @Expose
    val videos: ListVideosDTO,
    @SerializedName("credits")
    @Expose
    val credits: ListCreditsDTO
)
/*productionCountry*/
data class ProductionCountryDTO(
    @SerializedName("iso_3166_1")
    @Expose
    val iso:String,
    @SerializedName("name")
    @Expose
    val name:String
)

/*Video*/
data class ListVideosDTO(
    @SerializedName("results")
    @Expose
    val results:List<VideoDTO>
)

data class VideoDTO(
    @SerializedName("key")
    @Expose
    val path:String,
    @SerializedName("site")
    @Expose
    val site:String,
    val thumbnail:String? = null
)

/*Credits*/
data class ListCreditsDTO(
    @SerializedName("cast")
    @Expose
    val cast:List<CastDTO>,
    @SerializedName("crew")
    @Expose
    val crew:List<CrewDTO>
)

data class CrewDTO(
    @SerializedName("id")
    @Expose
    val id:Long,
    @SerializedName("job")
    @Expose
    val job:String,
    @SerializedName("name")
    @Expose
    val name:String,
    @SerializedName("profile_path")
    @Expose
    val profilePath:String?
)

data class CastDTO(
    @SerializedName("name")
    @Expose
    val name:String,
    @SerializedName("profile_path")
    @Expose
    val profilePath:String?
)