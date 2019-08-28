package com.abdulmanov.core.network.dto.people

import com.google.gson.annotations.SerializedName

data class PeopleDetailsDTO(
    @SerializedName("birthday")
    val birthday:String?,
    @SerializedName("known_for_department")
    val knowForDepartment:String,
    @SerializedName("deathday")
    val deathday:String?,
    @SerializedName("id")
    val id:Long,
    @SerializedName("name")
    val name:String,
    @SerializedName("also_known_as")
    val alsoKnownAs:List<String>,
    @SerializedName("gender")
    val gender:Int,
    @SerializedName("biography")
    val biography:String,
    @SerializedName("place_of_birth")
    val placeOfBirth:String?,
    @SerializedName("profile_path")
    val profilePath:String?,
    @SerializedName("imdb_id")
    val imdbId:String,
    @SerializedName("movie_credits")
    val movieCredit: MovieCreditsDTO,
    @SerializedName("tv_credits")
    val tvCredits: TVCreditsDTO,
    @SerializedName("translations")
    val translations: TranslationsDTO,
    @SerializedName("external_ids")
    val peopleExternalIDsDTO: PeopleExternalIDsDTO
)

data class TranslationsDTO(
    @SerializedName("translations")
    val translations:List<TranslationBiographyDTO>
)

data class MovieCreditsDTO(
    @SerializedName("cast")
    val castPeople:List<PeopleMovieCastDTO>,
    @SerializedName("crew")
    val crewPeople:List<PeopleMovieCrewDTO>
)

data class TVCreditsDTO(
    @SerializedName("cast")
    val castPeople:List<PeopleTVCastDTO>,
    @SerializedName("crew")
    val crewPeople:List<PeopleTVCrewDTO>
)






