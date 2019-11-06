package com.abdulmanov.domain.converters

import com.abdulmanov.core.storage.models.MovieEntity
import com.abdulmanov.domain.models.movies.MovieDetails
import com.abdulmanov.domain.models.movies.MovieMedium

interface MoviesDbConverter {

    fun convertMovieDetailsToMovieEntity(movieDetails: MovieDetails):MovieEntity

    fun convertMovieEntityToMovieMedium(movieEntity: MovieEntity):MovieMedium
}