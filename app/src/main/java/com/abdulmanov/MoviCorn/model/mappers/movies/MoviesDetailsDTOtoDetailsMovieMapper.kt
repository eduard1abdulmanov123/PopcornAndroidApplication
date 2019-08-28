package com.abdulmanov.MoviCorn.model.mappers.movies

import android.content.Context
import com.abdulmanov.core.network.dto.movies.MoviesDetailsDTO
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.MoviCorn.common.Constants.Network.Companion.BASE_POSTER_PATH_URL_550
import com.abdulmanov.MoviCorn.common.Constants.Network.Companion.THE_MOVIE_DB_MOVIE_BASE_URL
import com.abdulmanov.MoviCorn.model.vo.movie.DetailsMovie
import io.reactivex.functions.Function

class MoviesDetailsDTOtoDetailsMovieMapper(
    private val context:Context,
    private val movieCreditMapper: ListCreditsDTOtoListMovieCreditMapper,
    private val movieVideoMapper: ListVideosDTOtoListMovieVideoMapper,
    private val similarMapper:MoviesDTOtoListFilmLittleMapper
):Function<MoviesDetailsDTO, DetailsMovie> {

    override fun apply(moviesDetailsDTO: MoviesDetailsDTO): DetailsMovie {
        return with(moviesDetailsDTO) {
            val creditsVO = movieCreditMapper.apply(credits)
            val videosVO = movieVideoMapper.apply(videos)
            val similarVO = similarMapper.apply(similar)
            val countriesVO = productionCountries.map { it.name }
            val genresVO = genres.map { it.name }

            DetailsMovie(
                id,
                posterPath?.let { BASE_POSTER_PATH_URL_550 + it  },
                title,
                originalTitle,
                releaseDate.split("-")[0],
                voteAverage,
                voteCount,
                countriesVO,
                genresVO,
                runtime?.let { getRuntime(it) },
                "$${String.format("%,d", budget).replace(',', ' ')}",
                "$${String.format("%,d", revenue).replace(',', ' ')}",
                overview,
                THE_MOVIE_DB_MOVIE_BASE_URL + id,
                creditsVO,
                videosVO,
                similarVO
            )
        }
    }

    private fun getRuntime(runtime: Int): String {
        val hoursNumber = runtime / 60
        val minutesNumber = runtime % 60
        val hoursString =
            if (hoursNumber == 0)
                ""
            else
                context.resources.getQuantityString(
                    R.plurals.runtime_hours,
                    hoursNumber,
                    hoursNumber
                ) + " "
        val minutesString =
            if (minutesNumber == 0)
                ""
            else
                context.resources.getQuantityString(
                    R.plurals.runtime_minutes,
                    minutesNumber,
                    minutesNumber
                )
        return "$hoursString$minutesString"
    }
}