package com.abdulmanov.core.database.db.dao


import androidx.room.*
import com.abdulmanov.core.database.db.entity.Movie
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY title LIMIT :limit OFFSET :offset")
    fun getMoviesSortTitle(limit:Int,offset:Int):Flowable<List<Movie>>

    @Query("SELECT * FROM movies WHERE id_movie = :id")
    fun existFilm(id:Long):Maybe<Movie>

    @Insert
    fun insert(movie:Movie)

    @Query("DELETE FROM movies WHERE id_movie = :id")
    fun delete(id:Long)

}