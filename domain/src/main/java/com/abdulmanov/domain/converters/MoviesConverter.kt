package com.abdulmanov.domain.converters

import com.abdulmanov.core.remote.models.discover.DiscoverMovieResponse
import com.abdulmanov.core.remote.models.genres.GenresMovieResponse
import com.abdulmanov.core.remote.models.movies.MoviesResponse
import com.abdulmanov.core.remote.models.search.SearchMoviesResponse
import com.abdulmanov.core.remote.models.trending.TrendingMoviesResponse
import com.abdulmanov.domain.models.movies.MovieMedium
import com.abdulmanov.domain.models.movies.MovieShort

interface MoviesConverter {
    fun convertFromMoviesResponseToListMovie(moviesResponse: MoviesResponse, genresMovieResponse: GenresMovieResponse):List<MovieMedium>

    fun convertFromSearchMoviesResponseToListMovie(searchMoviesResponse: SearchMoviesResponse, genresMovieResponse: GenresMovieResponse):List<MovieMedium>

    fun convertFromDiscoverMoviesResponseToListMovie(discoverMovieResponse: DiscoverMovieResponse, genresMovieResponse: GenresMovieResponse):List<MovieMedium>

    fun convertFromMoviesResponseToListMovieShort(moviesResponse: MoviesResponse):List<MovieShort>

    fun convertFromTrendingMoviesResponseToListMovieShort(trendingMoviesResponse: TrendingMoviesResponse):List<MovieShort>
}