package com.abdulmanov.domain.converters

import com.abdulmanov.core.remote.models.people.ExternalIdsApi
import com.abdulmanov.core.remote.models.people.MovieCastApi
import com.abdulmanov.core.remote.models.people.MovieCrewApi
import com.abdulmanov.core.remote.models.people.PeopleDetailsResponse
import com.abdulmanov.domain.models.people.ExternalIds
import com.abdulmanov.domain.models.movies.MovieShort
import com.abdulmanov.domain.models.people.PeopleDetails

interface PeopleConverter {
    fun convertFromPeopleDetailsResponseToPeopleDetails(peopleDetailsResponse: PeopleDetailsResponse): PeopleDetails

    fun convertFromExternalIdsApiToExternalIds(externalIdsApi: ExternalIdsApi,id:Long): ExternalIds

    fun convertFromMovieCastApiToMovieShort(movieCastApi: MovieCastApi): MovieShort

    fun convertFromMovieCrewApiToMovieShort(movieCrewApi: MovieCrewApi): MovieShort
}