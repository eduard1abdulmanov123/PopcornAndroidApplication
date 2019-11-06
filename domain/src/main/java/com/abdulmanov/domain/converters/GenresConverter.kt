package com.abdulmanov.domain.converters

import com.abdulmanov.core.remote.models.genres.GenresMovieResponse
import com.abdulmanov.domain.models.genres.Genre

interface GenresConverter {
    fun convertFromListIdToListGenre(genres:List<Int>, genresMovieResponse: GenresMovieResponse):List<String>

    fun convertFromGenresMovieResponseToListGenre(genresMovieResponse: GenresMovieResponse):List<Genre>
}