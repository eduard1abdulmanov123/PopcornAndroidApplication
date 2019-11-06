package com.abdulmanov.core.remote.services

import com.abdulmanov.core.remote.helpers.Constants.Companion.KEY
import com.abdulmanov.core.remote.models.discover.DiscoverMovieResponse
import com.abdulmanov.core.remote.models.genres.GenresMovieResponse
import com.abdulmanov.core.remote.models.movies.MovieDetailsResponse
import com.abdulmanov.core.remote.models.movies.MoviesResponse
import com.abdulmanov.core.remote.models.people.PeopleDetailsResponse
import com.abdulmanov.core.remote.models.search.SearchMoviesResponse
import com.abdulmanov.core.remote.models.trending.TrendingMoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDatabaseService {

    @GET("movie/{id}?api_key=$KEY&append_to_response=videos%2Ccredits%2Csimilar")
    fun getMovieDetails(
        @Path("id") id: Long,
        @Query("language") language: String
    ): Single<MovieDetailsResponse>

    @GET("movie/now_playing?api_key=$KEY")
    fun getNowPlayingMovies(
        @Query("page") page: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Single<MoviesResponse>

    @GET("movie/upcoming?api_key=$KEY")
    fun getUpcomingMovies(
        @Query("page") page: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Single<MoviesResponse>

    @GET("movie/top_rated?api_key=$KEY")
    fun getTopRatedMovies(
        @Query("page") page: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Single<MoviesResponse>

    @GET("movie/popular?api_key=$KEY")
    fun getPopularMovies(
        @Query("page") page: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Single<MoviesResponse>

    @GET("trending/{media_type}/{time_window}?api_key=$KEY")
    fun getTrending(
        @Path("media_type") mediaType:String,
        @Path("time_window") timeWindow:String
    ):Single<TrendingMoviesResponse>

    @GET("person/{person_id}?api_key=$KEY&append_to_response=movie_credits%2Ctv_credits%2Ctranslations%2Cexternal_ids")
    fun getPeopleDetails(
        @Path("person_id") id: Long,
        @Query("language") language: String
    ): Single<PeopleDetailsResponse>

    @GET("search/movie?api_key=$KEY")
    fun getSearchMovies(
        @Query("page") page: String,
        @Query("language") language: String,
        @Query("region") region: String,
        @Query("query") query: String
    ): Single<SearchMoviesResponse>

    @GET("discover/movie?api_key=$KEY")
    fun discoverMovies(
        @Query("page") page: String,
        @Query("language") language: String,
        @Query("region") region: String,
        @Query("sort_by") sort: String,
        @Query("with_genres") genres:String,
        @Query("vote_average.gte") voteAverageGte:Double,
        @Query("vote_average.lte") voteAverageLte:Double,
        @Query("release_date.gte") releaseDateGte:String,
        @Query("release_date.lte") releaseDateLte: String
    ):Single<DiscoverMovieResponse>

    @GET("genre/movie/list?api_key=$KEY")
    fun getGenres(
        @Query("language") language: String
    ): Single<GenresMovieResponse>
}