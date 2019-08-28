package com.abdulmanov.core.database.db.dao


import androidx.room.*
import com.abdulmanov.core.database.db.entity.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY title LIMIT :limit OFFSET :offset")
    fun getMoviesSortTitle(limit: Int, offset: Int):List<Movie>

    @Query("SELECT * FROM movies WHERE id_movie = :id")
    fun existFilm(id: Long): Movie?

    @Insert
    fun insert(movie: Movie)

    @Query("UPDATE movies SET vote_count = :voteCount, vote_average = :voteAverage WHERE id_movie = :id")
    fun update(id: Long, voteCount: Long, voteAverage: Double)

    @Query("DELETE FROM movies WHERE id_movie = :id")
    fun delete(id: Long)
}