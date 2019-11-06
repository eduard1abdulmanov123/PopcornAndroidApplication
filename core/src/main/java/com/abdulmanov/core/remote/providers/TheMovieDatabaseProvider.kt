package com.abdulmanov.core.remote.providers

import com.abdulmanov.core.remote.models.discover.DiscoverMovieResponse
import com.abdulmanov.core.remote.models.genres.GenresMovieResponse
import com.abdulmanov.core.remote.models.movies.MovieDetailsResponse
import com.abdulmanov.core.remote.models.movies.MoviesResponse
import com.abdulmanov.core.remote.models.people.PeopleDetailsResponse
import com.abdulmanov.core.remote.models.search.SearchMoviesResponse
import com.abdulmanov.core.remote.models.trending.TrendingMoviesResponse
import io.reactivex.Single

interface TheMovieDatabaseProvider {

    fun fetchNowPlayingMovies(page: String, lan: String, reg: String): Single<MoviesResponse>

    fun fetchUpcomingMovies(page: String, lan: String, reg: String): Single<MoviesResponse>

    fun fetchTopRatedMovies(page: String, lan: String, reg: String): Single<MoviesResponse>

    fun fetchPopularMovies(page: String, lan: String, reg: String): Single<MoviesResponse>

    fun fetchTrendingMovies(mediaType: String, timeWindow: String): Single<TrendingMoviesResponse>

    fun fetchMovieDetails(id: Long, lan: String): Single<MovieDetailsResponse>

    fun fetchPeopleDetails(id: Long, lang: String): Single<PeopleDetailsResponse>

    fun searchMovies(
        page: String,
        lan: String,
        reg: String,
        query: String
    ): Single<SearchMoviesResponse>

    fun discoverMovies(
        page: String,
        lan: String,
        reg: String,
        sort: String,
        genres: String,
        voteAverageGte: Double,
        voteAverageLte: Double,
        releaseDateGte: String,
        releaseDateLte: String
    ): Single<DiscoverMovieResponse>

    fun fetchGenres(lang: String): Single<GenresMovieResponse>
}