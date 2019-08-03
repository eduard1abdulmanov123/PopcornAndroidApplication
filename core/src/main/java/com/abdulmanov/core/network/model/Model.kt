package com.abdulmanov.core.network.model



import com.abdulmanov.core.network.dto.movies.FilmDetailsDTO
import com.abdulmanov.core.network.dto.movies.MoviesDTO
import io.reactivex.Observable

interface Model {
    fun getNowPlayingMovies(page: String, lang: String, reg: String): Observable<MoviesDTO>

    fun getUpcomingMovies(page: String, lang: String, reg: String): Observable<MoviesDTO>

    fun getTopRatedMovies(page: String, lang: String, reg: String): Observable<MoviesDTO>

    fun getPopularMovies(page: String, lang: String, reg: String): Observable<MoviesDTO>

    fun getSearchMovies(page: String, lang: String, reg: String, key: String): Observable<MoviesDTO>

    fun getDetailFilm(id: Int, lang: String): Observable<FilmDetailsDTO>
}