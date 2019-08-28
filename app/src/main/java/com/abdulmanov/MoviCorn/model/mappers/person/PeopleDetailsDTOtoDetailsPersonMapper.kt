package com.abdulmanov.MoviCorn.model.mappers.person

import android.content.Context
import android.util.Log
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.Constants.Network.Companion.BASE_PROFILE_PATH_URL_550
import com.abdulmanov.MoviCorn.common.Constants.Network.Companion.FACEBOOK_PERSON_BASE_URL
import com.abdulmanov.MoviCorn.common.Constants.Network.Companion.IMDB_PERSON_BASE_URL
import com.abdulmanov.MoviCorn.common.Constants.Network.Companion.INSTAGRAM_PERSON_BASE_URL
import com.abdulmanov.MoviCorn.common.Constants.Network.Companion.THE_MOVIE_DB_PERSON_BASE_URL
import com.abdulmanov.MoviCorn.common.Constants.Network.Companion.TWITTER_PERSON_BASE_URL
import com.abdulmanov.MoviCorn.model.vo.person.ExternalPersonIDs
import com.abdulmanov.MoviCorn.model.vo.person.DetailsPerson
import com.abdulmanov.MoviCorn.model.vo.person.FilmographyPerson
import com.abdulmanov.core.network.dto.people.PeopleDetailsDTO
import io.reactivex.functions.Function

class PeopleDetailsDTOtoDetailsPersonMapper(
    private val context:Context,
    private val castMapper:PeopleMovieCastDTOtoFilmLittleMapper,
    private val crewMapper:PeopleMovieCrewDTOtoFilmLittleMapper
):Function<PeopleDetailsDTO, DetailsPerson> {

    override fun apply(peopleDetailsDTO: PeopleDetailsDTO): DetailsPerson {
        return with(peopleDetailsDTO){
            val movie = FilmographyPerson(
                movieCredit.castPeople.map { castMapper.apply(it) }.toSet().toList(),
                movieCredit.crewPeople.map { crewMapper.apply(it) }.toSet().toList()
            )
            val biographyCredit =
                if(biography.isEmpty()) {
                    with(translations.translations.find { it.name == "English" }?.data?.biography){
                        if(isNullOrEmpty()) null else this
                    }
                }
                else {
                    biography
                }
            val externalPersonIds = ExternalPersonIDs(
                peopleExternalIDsDTO.imdbId?.let {  IMDB_PERSON_BASE_URL + it},
                peopleExternalIDsDTO.facebookId?.let {  FACEBOOK_PERSON_BASE_URL + it},
                peopleExternalIDsDTO.twitterId?.let {  TWITTER_PERSON_BASE_URL + it},
                peopleExternalIDsDTO.instagramId?.let {  INSTAGRAM_PERSON_BASE_URL + it},
                THE_MOVIE_DB_PERSON_BASE_URL + id.toString()
            )

            DetailsPerson(
                id,
                name,
                getGender(gender),
                biographyCredit,
                placeOfBirth,
                profilePath?.let { BASE_PROFILE_PATH_URL_550 + it },
                movie,
                externalPersonIds
            )
        }
    }

    private fun getGender(gender:Int):String{
        Log.d("CreditDetailsView", gender.toString())
        return when(gender){
            2 -> context.getString(R.string.man)
            1 -> context.getString(R.string.woman)
            0 -> context.getString(R.string.not_specified)
            else -> context.getString(R.string.not_specified)
        }
    }
}