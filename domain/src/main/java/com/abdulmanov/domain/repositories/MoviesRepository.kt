package com.abdulmanov.domain.repositories

import com.abdulmanov.domain.models.genres.Genre
import com.abdulmanov.domain.models.movies.MovieMedium
import com.abdulmanov.domain.models.movies.MovieDetails
import com.abdulmanov.domain.models.movies.MovieShort
import com.abdulmanov.domain.models.movies.PackageMovies
import com.abdulmanov.domain.models.people.PeopleDetails
import io.reactivex.Single

interface MoviesRepository {

    fun fetchPackageMovies(lang: String,reg: String):Single<PackageMovies>

    fun fetchMovieDetails(id:Long,lang:String): Single<MovieDetails>

    fun fetchPeopleDetails(id:Long,lang:String): Single<PeopleDetails>

    fun fetchNowPlayingMovies(page: String, lan: String, reg: String): Single<List<MovieMedium>>

    fun fetchUpcomingMovies(page: String, lan: String, reg: String): Single<List<MovieMedium>>

    fun fetchTopRatedMovies(page: String, lan: String, reg: String): Single<List<MovieMedium>>

    fun fetchPopularMovies(page: String, lan: String, reg: String): Single<List<MovieMedium>>

    fun fetchTrendingMovies(mediaType: String, timeWindow: String): Single<List<MovieShort>>

    fun searchMovies(
        page: String,
        lan: String,
        reg: String,
        query: String
    ): Single<List<MovieMedium>>

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
    ): Single<List<MovieMedium>>

    fun fetchGenres(lang:String):Single<List<Genre>>
}