package com.abdulmanov.domain.models.people

data class PeopleDetails(
    val id:Long,
    val name:String,
    val gender:Int,
    val biography:String?,
    val placeOfBirth:String?,
    val profilePath:String?,
    val filmographyPerson: MovieCredits,
    val externalPersonIDs: ExternalIds
)