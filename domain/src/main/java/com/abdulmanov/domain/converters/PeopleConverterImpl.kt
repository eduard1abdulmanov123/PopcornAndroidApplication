package com.abdulmanov.domain.converters

import com.abdulmanov.core.remote.models.people.ExternalIdsApi
import com.abdulmanov.core.remote.models.people.MovieCastApi
import com.abdulmanov.core.remote.models.people.MovieCrewApi
import com.abdulmanov.core.remote.models.people.PeopleDetailsResponse
import com.abdulmanov.domain.helpers.Constants
import com.abdulmanov.domain.helpers.Constants.Companion.BASE_IMAGE_URL_780
import com.abdulmanov.domain.helpers.Constants.Companion.FACEBOOK_PERSON_BASE_URL
import com.abdulmanov.domain.helpers.Constants.Companion.IMDB_PERSON_BASE_URL
import com.abdulmanov.domain.helpers.Constants.Companion.INSTAGRAM_PERSON_BASE_URL
import com.abdulmanov.domain.helpers.Constants.Companion.THE_MOVIE_DB_PERSON_BASE_URL
import com.abdulmanov.domain.helpers.Constants.Companion.TWITTER_PERSON_BASE_URL
import com.abdulmanov.domain.models.people.ExternalIds
import com.abdulmanov.domain.models.people.MovieCredits
import com.abdulmanov.domain.models.movies.MovieShort
import com.abdulmanov.domain.models.people.PeopleDetails

class PeopleConverterImpl:PeopleConverter {
    override fun convertFromPeopleDetailsResponseToPeopleDetails(peopleDetailsResponse: PeopleDetailsResponse): PeopleDetails {
        return with(peopleDetailsResponse){
            val biographyPeople =
                if(biography.isEmpty()) {
                    with(translations.translations.find { it.name == "English" }?.data?.biography){
                        if(isNullOrEmpty()) null else this
                    }
                }
                else {
                    biography
                }
            val movieCredits = MovieCredits(
                movie_credits.cast.map { convertFromMovieCastApiToMovieShort(it) }.toSet().toList(),
                movie_credits.crew.map { convertFromMovieCrewApiToMovieShort(it) }.toSet().toList()
            )
            PeopleDetails(
                id,
                name,
                gender,
                biographyPeople,
                place_of_birth,
                profile_path?.let { BASE_IMAGE_URL_780 + it },
                movieCredits,
                convertFromExternalIdsApiToExternalIds(external_ids, id)
            )
        }
    }

    override fun convertFromExternalIdsApiToExternalIds(externalIdsApi: ExternalIdsApi,id:Long): ExternalIds {
        return with(externalIdsApi){
            ExternalIds(
                if(!imdb_id.isNullOrEmpty()) IMDB_PERSON_BASE_URL + imdb_id else null,
                facebook_id?.let { FACEBOOK_PERSON_BASE_URL + it },
                twitter_id?.let { TWITTER_PERSON_BASE_URL + it },
                if(!instagram_id.isNullOrEmpty())  INSTAGRAM_PERSON_BASE_URL + instagram_id else null,
                THE_MOVIE_DB_PERSON_BASE_URL + id
            )
        }
    }

    override fun convertFromMovieCastApiToMovieShort(movieCastApi: MovieCastApi): MovieShort {
        return with(movieCastApi){
            MovieShort(
                id,
                title,
                vote_average.toString(),
                release_date?.let{it.split("-")[0]},
                poster_path?.let { path-> Constants.BASE_IMAGE_URL_185 + path },
                backdrop_path?.let { path-> Constants.BASE_IMAGE_URL_780 + path }
            )
        }
    }

    override fun convertFromMovieCrewApiToMovieShort(movieCrewApi: MovieCrewApi): MovieShort {
        return with(movieCrewApi){
            MovieShort(
                id,
                title,
                vote_average.toString(),
                release_date?.let{it.split("-")[0]},
                poster_path?.let { path-> Constants.BASE_IMAGE_URL_185 + path },
                backdrop_path?.let { path-> Constants.BASE_IMAGE_URL_780 + path }
            )
        }
    }

}