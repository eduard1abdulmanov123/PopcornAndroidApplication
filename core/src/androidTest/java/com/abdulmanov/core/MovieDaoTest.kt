package com.abdulmanov.core

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.abdulmanov.core.database.db.MoviesDataBase
import com.abdulmanov.core.database.db.dao.MovieDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {
    lateinit var db:MoviesDataBase
    lateinit var movieDao:MovieDao

    @Before
    fun createDb(){
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            MoviesDataBase::class.java
        ).allowMainThreadQueries().build()

        movieDao = db.movieDao()
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun insertMoviesThenGetMovies(){
        val random = Random.nextInt(0,4124)
        val movies = TestHelper.createMovies(random)
        movies.forEach {movieDao.insert(it)}
        val test = movieDao.getMoviesSortTitle(random,0)
        Assert.assertEquals(movies.size,test.size)
    }

    @Test
    fun insertMoviesThenExistMovie(){
        val random = Random.nextInt(0,4124)
        val movies = TestHelper.createMovies(random)
        movies.forEach {movieDao.insert(it)}
        val test = movieDao.existFilm(movies[movies.size-1].idMovie)
        Assert.assertEquals(movies[movies.size-1],test)
    }

    @Test
    fun insertMoviesThenDeleteMovie(){
        val random = Random.nextInt(0,4124)
        val movies = TestHelper.createMovies(random)
        movies.forEach {movieDao.insert(it)}
        movieDao.delete(movies[movies.size-1].idMovie)
        val test = movieDao.getMoviesSortTitle(random,0)
        Assert.assertNull(test.find { it==movies[movies.size-1]})
        Assert.assertNull(movieDao.existFilm(movies[movies.size-1].idMovie))
    }

    @Test
    fun insertMoviesThenUpdateMovie(){
        val random = Random.nextInt(0,4124)
        val movies = TestHelper.createMovies(random)
        movies.forEach {movieDao.insert(it)}
        val voteCount =  Random.nextLong(0,41243124)
        val voteAverage = Random.nextDouble(0.1,3124124.3)
        movieDao.update(movies[movies.size-1].idMovie,voteCount,voteAverage)
        val test = movieDao.getMoviesSortTitle(random,0)
        val movieTest = test.find { it.idMovie == movies[movies.size-1].idMovie }
        Assert.assertEquals(movieTest?.voteCount,voteCount)
        Assert.assertEquals(movieTest?.voteAverage,voteAverage)
    }
}