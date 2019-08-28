package com.abdulmanov.core.network.api


import com.abdulmanov.core.common.Constant.Network.Companion.KEY_MOVIES
import com.abdulmanov.core.network.dto.genres.GenresMovieDTO
import com.abdulmanov.core.network.dto.people.PeopleDetailsDTO
import com.abdulmanov.core.network.dto.movies.MoviesDetailsDTO
import com.abdulmanov.core.network.dto.movies.MoviesDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiMoviesInterface {

    @GET("movie/now_playing?api_key=$KEY_MOVIES")
    fun getNowPlayingMovies(
        @Query("page") page: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Call<MoviesDTO>

    @GET("movie/upcoming?api_key=$KEY_MOVIES")
    fun getUpcomingMovies(
        @Query("page") page: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Call<MoviesDTO>

    @GET("movie/top_rated?api_key=$KEY_MOVIES")
    fun getTopRatedMovies(
        @Query("page") page: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Call<MoviesDTO>

    @GET("movie/popular?api_key=$KEY_MOVIES")
    fun getPopularMovies(
        @Query("page") page: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Call<MoviesDTO>

    @GET("search/movie?api_key=$KEY_MOVIES")
    fun getSearchMovies(
        @Query("page") page: String,
        @Query("language") language: String,
        @Query("region") region: String,
        @Query("query") query: String
    ): Call<MoviesDTO>

    @GET("movie/{id}?api_key=$KEY_MOVIES&append_to_response=videos%2Ccredits%2Csimilar")
    fun getDetailFilm(
        @Path("id") id: Long,
        @Query("language") language: String
    ): Call<MoviesDetailsDTO>

    @GET("person/{person_id}?api_key=$KEY_MOVIES&append_to_response=movie_credits%2Ctv_credits%2Ctranslations%2Cexternal_ids")
    fun getDetailCredit(
        @Path("person_id") personId: Long,
        @Query("language") language: String
    ): Call<PeopleDetailsDTO>

    @GET("genre/movie/list?api_key=$KEY_MOVIES")
    fun getGenres(
        @Query("language") language: String
    ): Call<GenresMovieDTO>
}
