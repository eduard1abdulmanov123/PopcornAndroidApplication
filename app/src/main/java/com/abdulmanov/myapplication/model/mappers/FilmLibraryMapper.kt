package com.abdulmanov.myapplication.model.mappers

import com.abdulmanov.core.database.db.entity.Movie
import com.abdulmanov.myapplication.model.vo.FilmLibrary
import io.reactivex.functions.Function

class FilmLibraryMapper:Function<List<Movie>,List<FilmLibrary>> {

    override fun apply(movies: List<Movie>): List<FilmLibrary> {
        return movies.map {
            FilmLibrary(
                it.idMovie,
                it.title,
                it.posterPath,
                it.overview,
                it.releaseDate.split('-')[0],
                it.genres
            )
        }
    }
}