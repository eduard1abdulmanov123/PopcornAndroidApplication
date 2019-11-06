package com.abdulmanov.domain.converters

import com.abdulmanov.core.remote.models.discover.DiscoverMovieResponse
import com.abdulmanov.core.remote.models.genres.GenresMovieResponse
import com.abdulmanov.core.remote.models.movies.MoviesResponse
import com.abdulmanov.core.remote.models.search.SearchMoviesResponse
import com.abdulmanov.core.remote.models.trending.TrendingMoviesResponse
import com.abdulmanov.domain.helpers.Constants
import com.abdulmanov.domain.models.movies.MovieMedium
import com.abdulmanov.domain.models.movies.MovieShort

class MoviesConverterImpl(private val genresConverter: GenresConverter):MoviesConverter {

    override fun convertFromMoviesResponseToListMovie(
        moviesResponse: MoviesResponse,
        genresMovieResponse: GenresMovieResponse
    ): List<MovieMedium> {
        return moviesResponse.results.map {
            getMovie(
                it.id,
                it.vote_average,
                it.vote_count,
                it.title,
                it.poster_path,
                it.overview,
                it.release_date,
                it.genre_ids,
                genresMovieResponse
            )
        }
    }

    override fun convertFromSearchMoviesResponseToListMovie(
        searchMoviesResponse: SearchMoviesResponse,
        genresMovieResponse: GenresMovieResponse
    ): List<MovieMedium> {
        return searchMoviesResponse.results.map {
            getMovie(
                it.id,
                it.vote_average,
                it.vote_count,
                it.title,
                it.poster_path,
                it.overview,
                it.release_date,
                it.genre_ids,
                genresMovieResponse
            )
        }
    }

    override fun convertFromDiscoverMoviesResponseToListMovie(
        discoverMovieResponse: DiscoverMovieResponse,
        genresMovieResponse: GenresMovieResponse
    ): List<MovieMedium> {
        return discoverMovieResponse.results.map {
            getMovie(
                it.id,
                it.vote_average,
                it.vote_count,
                it.title,
                it.poster_path,
                it.overview,
                it.release_date,
                it.genre_ids,
                genresMovieResponse
            )
        }
    }

    override fun convertFromMoviesResponseToListMovieShort(moviesResponse: MoviesResponse): List<MovieShort> {
        return moviesResponse.results.map {
            getMovieShort(it.id,it.title,it.vote_average,it.release_date,it.poster_path,it.backdrop_path)
        }
    }

    override fun convertFromTrendingMoviesResponseToListMovieShort(trendingMoviesResponse: TrendingMoviesResponse): List<MovieShort> {
        return trendingMoviesResponse.results.map {
            getMovieShort(it.id,it.title,it.vote_average,it.release_date,it.poster_path,it.backdrop_path)
        }
    }

    private fun getMovie(
        id:Long,
        voteAverage:Double,
        voteCount:Long,
        title:String,
        posterPath: String?,
        overview:String,
        releaseDate: String?,
        genreIds:List<Int>,
        genresMovieResponse: GenresMovieResponse
    ):MovieMedium{
        return MovieMedium(
            id,
            voteAverage.toString(),
            String.format("%,d", voteCount),
            title,
            posterPath?.let { path -> Constants.BASE_IMAGE_URL_185 + path },
            overview,
            releaseDate,
            genresConverter.convertFromListIdToListGenre(genreIds, genresMovieResponse)
        )
    }

    private fun getMovieShort(
        id: Long,
        title: String,
        voteAverage: Double,
        releaseDate: String,
        posterPath: String?,
        backdropPath: String?
    ): MovieShort {
        return MovieShort(
            id,
            title,
            voteAverage.toString(),
            releaseDate.split("-")[0],
            posterPath?.let { path -> Constants.BASE_IMAGE_URL_185 + path },
            backdropPath.let { path -> Constants.BASE_IMAGE_URL_780 + path }
        )
    }
}