package com.abdulmanov.core.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.abdulmanov.core.storage.contracts.RoomContract
import com.abdulmanov.core.storage.models.MovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface MovieDao {
    @Query("SELECT * FROM ${RoomContract.TABLE_MOVIES} ORDER BY title")
    fun fetchMoviesSortTitle(): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM ${RoomContract.TABLE_MOVIES} WHERE id = :id")
    fun existMovie(id: Long): Single<List<MovieEntity>>

    @Insert
    fun insertMovie(movie: MovieEntity): Completable

    @Query("UPDATE ${RoomContract.TABLE_MOVIES} SET voteCount = :voteCount, voteAverage = :voteAverage WHERE id = :id")
    fun updateMovie(id: Long, voteCount: Long, voteAverage: Double): Completable

    @Query("DELETE FROM ${RoomContract.TABLE_MOVIES} WHERE id = :id")
    fun deleteMovie(id: Long): Completable

    @Query("DELETE FROM ${RoomContract.TABLE_MOVIES} WHERE id in (:ids)")
    fun deleteMovies(ids: List<Long>): Completable
}