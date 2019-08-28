package com.abdulmanov.MoviCorn.model.mappers.movies

import com.abdulmanov.core.network.dto.movies.MoviesDTO

import com.abdulmanov.MoviCorn.common.Constants.Network.Companion.BASE_POSTER_PATH_URL_550
import com.abdulmanov.MoviCorn.model.vo.movie.FilmMedium
import io.reactivex.functions.Function

class MoviesDTOtoListFilmMediumMapper:Function<MoviesDTO,List<FilmMedium>> {
    override fun apply(moviesDTO: MoviesDTO): List<FilmMedium> {
        return moviesDTO.results.map { filmDTO ->
            FilmMedium(
                filmDTO.id,
                filmDTO.voteAverage.toString(),
                String.format("%,d", filmDTO.voteCount),
                filmDTO.title,
                filmDTO.posterPath?.let { BASE_POSTER_PATH_URL_550 + it },
                filmDTO.overview,
                filmDTO.releaseDate,
                filmDTO.genres.joinToString(", ")
            )
        }
    }
}