package com.abdulmanov.domain.converters

import com.abdulmanov.core.remote.models.movies.*
import com.abdulmanov.domain.models.movies.*
import com.abdulmanov.domain.models.movies.Credit
import com.abdulmanov.domain.models.movies.Video

interface MovieDetailsConverter {

    fun convertFromMovieDetailsResponseToMovieDetails(movieDetailsResponse: MovieDetailsResponse,inLocalDb:Boolean = false): MovieDetails

    fun convertListProductionCountryApiToListCountry(listProductionCountryApi: List<ProductionCountryApi>):List<String>

    fun convertCreditsApiToListCredit(creditsApi: CreditsApi):List<Credit>

    fun convertResultVideosApiToListVideo(resultVideosApi: ResultVideosApi):List<Video>

}