package com.abdulmanov.core.network.model



import com.abdulmanov.core.network.dto.people.PeopleDetailsDTO
import com.abdulmanov.core.network.dto.movies.MoviesDetailsDTO
import com.abdulmanov.core.network.dto.movies.MoviesDTO
import io.reactivex.Single

interface Model {
    fun getNowPlayingMovies(page: String, lang: String, reg: String): Single<MoviesDTO>

    fun getUpcomingMovies(page: String, lang: String, reg: String): Single<MoviesDTO>

    fun getTopRatedMovies(page: String, lang: String, reg: String): Single<MoviesDTO>

    fun getPopularMovies(page: String, lang: String, reg: String): Single<MoviesDTO>

    fun getRecommendationMovies():Single<MoviesDTO>

    fun getSearchMovies(page: String, lang: String, reg: String, key: String): Single<MoviesDTO>

    fun getDetailFilm(id: Long, lang: String): Single<MoviesDetailsDTO>

    fun getPeopleDetails(id:Long, lang: String): Single<PeopleDetailsDTO>
}