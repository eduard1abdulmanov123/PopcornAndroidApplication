package com.abdulmanov.domain.converters

import com.abdulmanov.core.storage.models.MovieEntity
import com.abdulmanov.domain.models.movies.MovieDetails
import com.abdulmanov.domain.models.movies.MovieMedium

class MoviesDbConverterImpl: MoviesDbConverter {

    override fun convertMovieDetailsToMovieEntity(movieDetails: MovieDetails): MovieEntity {
        return with(movieDetails){
            MovieEntity(
                id,
                title,
                overview,
                releaseData,
                genres,
                posterPath,
                voteCount.toLong(),
                voteAverage.toDouble()
            )
        }
    }

    override fun convertMovieEntityToMovieMedium(movieEntity: MovieEntity): MovieMedium {
        return with(movieEntity){
            MovieMedium(
                id,
                voteAverage.toString(),
                voteCount.toString(),
                title,
                posterPath,
                overview?: "",
                releaseDate,
                genres
            )
        }
    }
}