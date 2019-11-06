package com.abdulmanov.domain.repositories

import com.abdulmanov.core.remote.models.discover.DiscoverMovieResponse
import com.abdulmanov.core.remote.models.genres.GenresMovieResponse
import com.abdulmanov.core.remote.models.movies.MovieDetailsResponse
import com.abdulmanov.core.remote.models.movies.MoviesResponse
import com.abdulmanov.core.remote.models.search.SearchMoviesResponse
import com.abdulmanov.core.remote.models.trending.TrendingMoviesResponse
import com.abdulmanov.core.remote.providers.TheMovieDatabaseProvider
import com.abdulmanov.core.storage.RoomMoviesDatabase
import com.abdulmanov.core.storage.models.MovieEntity
import com.abdulmanov.domain.converters.*
import com.abdulmanov.domain.models.genres.Genre
import com.abdulmanov.domain.models.movies.MovieDetails
import com.abdulmanov.domain.models.movies.MovieMedium
import com.abdulmanov.domain.models.movies.MovieShort
import com.abdulmanov.domain.models.movies.PackageMovies
import com.abdulmanov.domain.models.people.PeopleDetails
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function5
import io.reactivex.schedulers.Schedulers

class MoviesRepositoryImpl(
    private val theMovieDatabaseProvider: TheMovieDatabaseProvider,
    private val roomMoviesDatabase: RoomMoviesDatabase,
    private val movieDetailsConverter: MovieDetailsConverter,
    private val moviesConverter: MoviesConverter,
    private val peopleConverter: PeopleConverter,
    private val genresConverter: GenresConverter
):MoviesRepository {

    override fun fetchPackageMovies(lang: String, reg: String): Single<PackageMovies> {
        return Single.zip(
            theMovieDatabaseProvider.fetchTrendingMovies("movie", "day"),
            theMovieDatabaseProvider.fetchNowPlayingMovies("1", lang, reg),
            theMovieDatabaseProvider.fetchUpcomingMovies("1", lang, reg),
            theMovieDatabaseProvider.fetchPopularMovies("1", lang, reg),
            theMovieDatabaseProvider.fetchTopRatedMovies("1", lang, reg),
            Function5<TrendingMoviesResponse, MoviesResponse, MoviesResponse, MoviesResponse, MoviesResponse, PackageMovies> { recommendations, nowPlaying, upcoming, populars, topRated ->
                PackageMovies(
                    moviesConverter.convertFromTrendingMoviesResponseToListMovieShort(
                        recommendations
                    ),
                    moviesConverter.convertFromMoviesResponseToListMovieShort(nowPlaying),
                    moviesConverter.convertFromMoviesResponseToListMovieShort(upcoming),
                    moviesConverter.convertFromMoviesResponseToListMovieShort(populars),
                    moviesConverter.convertFromMoviesResponseToListMovieShort(topRated)
                )
            }
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchMovieDetails(id: Long, lang: String): Single<MovieDetails> {
        return Single.zip(
            theMovieDatabaseProvider.fetchMovieDetails(id,lang),
            roomMoviesDatabase.movieDao().existMovie(id),
            BiFunction<MovieDetailsResponse,List<MovieEntity>,MovieDetails>{movieDetailsResponse,listMovieEntity->
                movieDetailsConverter.convertFromMovieDetailsResponseToMovieDetails(
                    movieDetailsResponse,
                    listMovieEntity.isNotEmpty()
                )
            }
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchPeopleDetails(id: Long, lang: String): Single<PeopleDetails> {
        return theMovieDatabaseProvider.fetchPeopleDetails(id,lang)
            .map { peopleConverter.convertFromPeopleDetailsResponseToPeopleDetails(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchNowPlayingMovies(page: String, lan: String, reg: String): Single<List<MovieMedium>> {
        return getSingleListMovie(theMovieDatabaseProvider.fetchNowPlayingMovies(page,lan,reg),lan)
    }

    override fun fetchUpcomingMovies(page: String, lan: String, reg: String): Single<List<MovieMedium>> {
        return getSingleListMovie(theMovieDatabaseProvider.fetchUpcomingMovies(page,lan,reg),lan)
    }

    override fun fetchTopRatedMovies(page: String, lan: String, reg: String): Single<List<MovieMedium>> {
        return getSingleListMovie(theMovieDatabaseProvider.fetchTopRatedMovies(page,lan,reg),lan)
    }

    override fun fetchPopularMovies(page: String, lan: String, reg: String): Single<List<MovieMedium>> {
        return getSingleListMovie(theMovieDatabaseProvider.fetchPopularMovies(page,lan,reg),lan)
    }

    override fun fetchTrendingMovies(
        mediaType: String,
        timeWindow: String
    ): Single<List<MovieShort>> {
        return theMovieDatabaseProvider.fetchTrendingMovies(mediaType,timeWindow)
            .map { moviesConverter.convertFromTrendingMoviesResponseToListMovieShort(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun searchMovies(
        page: String,
        lan: String,
        reg: String,
        query: String
    ): Single<List<MovieMedium>> {
        return Single.zip(
            theMovieDatabaseProvider.searchMovies(page,lan,reg,query),
            theMovieDatabaseProvider.fetchGenres(lan),
            BiFunction<SearchMoviesResponse,GenresMovieResponse,List<MovieMedium>>{ searchMoviesResponse, genresMovieResponse->
                moviesConverter.convertFromSearchMoviesResponseToListMovie(searchMoviesResponse,genresMovieResponse)
            }
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
    ): Single<List<MovieMedium>> {
        return Single.zip(
            theMovieDatabaseProvider.discoverMovies(page,lan,reg,sort,genres,voteAverageGte,voteAverageLte,releaseDateGte,releaseDateLte),
            theMovieDatabaseProvider.fetchGenres(lan),
            BiFunction<DiscoverMovieResponse,GenresMovieResponse,List<MovieMedium>>{ discoverMoviesResponse, genresMovieResponse->
                moviesConverter.convertFromDiscoverMoviesResponseToListMovie(discoverMoviesResponse,genresMovieResponse)
            }
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    private fun getSingleListMovie(factory:Single<MoviesResponse>,lang:String):Single<List<MovieMedium>>{
        return Single.zip(
            factory,
            theMovieDatabaseProvider.fetchGenres(lang),
            BiFunction<MoviesResponse,GenresMovieResponse,List<MovieMedium>>{ moviesResponse, genresMovieResponse->
                moviesConverter.convertFromMoviesResponseToListMovie(moviesResponse,genresMovieResponse)
            }
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchGenres(lang: String): Single<List<Genre>> {
        return theMovieDatabaseProvider.fetchGenres(lang)
            .map { genresConverter.convertFromGenresMovieResponseToListGenre(it)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}