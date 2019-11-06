package com.abdulmanov.core.remote.providers

import com.abdulmanov.core.remote.helpers.RetrofitFactory
import com.abdulmanov.core.remote.models.discover.DiscoverMovieResponse
import com.abdulmanov.core.remote.models.genres.GenresMovieResponse
import com.abdulmanov.core.remote.models.movies.MovieDetailsResponse
import com.abdulmanov.core.remote.models.movies.MoviesResponse
import com.abdulmanov.core.remote.models.people.PeopleDetailsResponse
import com.abdulmanov.core.remote.models.search.SearchMoviesResponse
import com.abdulmanov.core.remote.models.trending.TrendingMoviesResponse
import io.reactivex.Single

class TheMovieDatabaseProviderImpl:TheMovieDatabaseProvider {

    private val theMovieDatabaseService = RetrofitFactory.getTheMovieDatabaseService()

    override fun fetchNowPlayingMovies(
        page: String,
        lan: String,
        reg: String
    ): Single<MoviesResponse> {
        return theMovieDatabaseService.getNowPlayingMovies(page, lan, reg)
    }

    override fun fetchUpcomingMovies(
        page: String,
        lan: String,
        reg: String
    ): Single<MoviesResponse> {
        return theMovieDatabaseService.getUpcomingMovies(page, lan, reg)
    }

    override fun fetchTopRatedMovies(
        page: String,
        lan: String,
        reg: String
    ): Single<MoviesResponse> {
        return theMovieDatabaseService.getTopRatedMovies(page, lan, reg)
    }

    override fun fetchPopularMovies(
        page: String,
        lan: String,
        reg: String
    ): Single<MoviesResponse> {
        return theMovieDatabaseService.getPopularMovies(page, lan, reg)
    }

    override fun fetchTrendingMovies(
        mediaType: String,
        timeWindow: String
    ): Single<TrendingMoviesResponse> {
        return theMovieDatabaseService.getTrending(mediaType, timeWindow)
    }

    override fun fetchMovieDetails(id: Long, lan: String): Single<MovieDetailsResponse> {
        return theMovieDatabaseService.getMovieDetails(id, lan)
    }

    override fun fetchPeopleDetails(id: Long, lang: String): Single<PeopleDetailsResponse> {
        return theMovieDatabaseService.getPeopleDetails(id, lang)
    }

    override fun searchMovies(
        page: String,
        lan: String,
        reg: String,
        query: String
    ): Single<SearchMoviesResponse> {
        return theMovieDatabaseService.getSearchMovies(page, lan, reg, query)
    }

    override fun discoverMovies(
        page: String,
        lan: String,
        reg: String,
        sort: String,
        genres: String,
        voteAverageGte: Double,
        voteAverageLte: Double,
        releaseDateGte: String,
        releaseDateLte: String
    ): Single<DiscoverMovieResponse> {
        return theMovieDatabaseService.discoverMovies(
            page,
            lan,
            reg,
            sort,
            genres,
            voteAverageGte,
            voteAverageLte,
            releaseDateGte,
            releaseDateLte
        )
    }

    override fun fetchGenres(lang: String): Single<GenresMovieResponse> {
        return theMovieDatabaseService.getGenres(lang)
    }
}