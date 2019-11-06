package com.abdulmanov.domain.repositories


import com.abdulmanov.core.storage.models.MovieEntity
import com.abdulmanov.domain.models.movies.MovieDetails
import com.abdulmanov.domain.models.movies.MovieMedium
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface MoviesDbRepository {

    fun fetchMovies(): Flowable<List<MovieMedium>>

    fun insertMovie(movie:MovieDetails):Completable

    fun updateMovie(id: Long, voteCount: Long, voteAverage: Double): Completable

    fun deleteMovie(id: Long): Completable

    fun deleteMovies(ids:List<Long>): Completable

    fun existsMovie(id:Long):Single<List<MovieEntity>>
}