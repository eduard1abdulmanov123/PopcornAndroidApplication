package com.abdulmanov.myapplication.model.mappers

import com.abdulmanov.core.network.dto.movies.MoviesDTO
import com.abdulmanov.myapplication.model.vo.FilmShort
import io.reactivex.functions.Function

class FilmShortMapper:Function<MoviesDTO,List<FilmShort>> {
    override fun apply(moviesDTO: MoviesDTO): List<FilmShort> {
        return moviesDTO.results.map { filmDTO ->
            FilmShort(
                filmDTO.id,
                filmDTO.voteAverage.toString(),
                String.format("%,d", filmDTO.voteCount),
                filmDTO.title,
                filmDTO.posterPath ?: "",
                filmDTO.overview,
                filmDTO.releaseDate,
                filmDTO.genres.joinToString(", ")
            )
        }
    }
}