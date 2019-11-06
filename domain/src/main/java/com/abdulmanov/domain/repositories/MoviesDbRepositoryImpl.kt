package com.abdulmanov.domain.repositories

import android.util.Log
import com.abdulmanov.core.storage.RoomMoviesDatabase
import com.abdulmanov.core.storage.models.MovieEntity
import com.abdulmanov.domain.converters.MoviesDbConverter
import com.abdulmanov.domain.models.movies.MovieDetails
import com.abdulmanov.domain.models.movies.MovieMedium
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesDbRepositoryImpl(
    private val roomMoviesDatabase: RoomMoviesDatabase,
    private val moviesDbConverter: MoviesDbConverter
):MoviesDbRepository {

    override fun fetchMovies(): Flowable<List<MovieMedium>> {
        return roomMoviesDatabase.movieDao().fetchMoviesSortTitle()
            .map { it.map{ element-> moviesDbConverter.convertMovieEntityToMovieMedium(element) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertMovie(movie: MovieDetails): Completable {
        return roomMoviesDatabase.movieDao().insertMovie(moviesDbConverter.convertMovieDetailsToMovieEntity(movie))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateMovie(id: Long, voteCount: Long, voteAverage: Double): Completable {
        return roomMoviesDatabase.movieDao().updateMovie(id,voteCount,voteAverage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteMovie(id: Long): Completable {
        return roomMoviesDatabase.movieDao().deleteMovie(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteMovies(ids: List<Long>): Completable {
        Log.d("DatabaseTesting",ids.toString())
        return roomMoviesDatabase.movieDao().deleteMovies(ids)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun existsMovie(id: Long): Single<List<MovieEntity>> {
        return roomMoviesDatabase.movieDao().existMovie(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}