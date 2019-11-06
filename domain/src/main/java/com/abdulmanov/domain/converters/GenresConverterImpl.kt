package com.abdulmanov.domain.converters

import com.abdulmanov.core.remote.models.genres.GenresMovieResponse
import com.abdulmanov.domain.models.genres.Genre

class GenresConverterImpl:GenresConverter {

    override fun convertFromListIdToListGenre(
        genres: List<Int>,
        genresMovieResponse: GenresMovieResponse
    ): List<String> {
        return genres.map { id -> genresMovieResponse.genres.find { it.id == id }!!.name }
    }

    override fun convertFromGenresMovieResponseToListGenre(genresMovieResponse: GenresMovieResponse): List<Genre> {
        return genresMovieResponse.genres.map { Genre(it.id,it.name) }
    }
}
