package com.abdulmanov.MoviCorn.model.vo.person

data class DetailsPerson(
    val id:Long,
    val name:String,
    val gender:String,
    val biography:String?,
    val placeOfBirth:String?,
    val profilePath:String?,
    val filmographyPerson: FilmographyPerson,
    val externalPersonIDs:ExternalPersonIDs
)
