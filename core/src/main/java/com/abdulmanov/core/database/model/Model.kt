package com.abdulmanov.core.database.model

import com.abdulmanov.core.database.db.entity.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe



interface Model {

    fun getMoviesSortTitle(page:Int): Flowable<List<Movie>>

    fun existsFilm(id:Long):Maybe<Movie>

    fun insertMovie(movie: Movie):Completable

    fun deleteMovie(id: Long):Completable
}