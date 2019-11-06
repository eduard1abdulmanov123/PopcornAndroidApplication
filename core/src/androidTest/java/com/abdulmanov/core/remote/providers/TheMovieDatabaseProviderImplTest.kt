package com.abdulmanov.core.remote.providers


import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class TheMovieDatabaseProviderImplTest {

    @Test
    fun fetchNowPlayingMovies() {
        for(i in 1..3) {
            TheMovieDatabaseProviderImpl().fetchNowPlayingMovies(i.toString(), "ru-RU", "RU").toObservable()
                .doOnNext {
                    it.results.forEach {result->
                        Log.d("Test",result.toString())
                    }
                }
                .test()
                .awaitDone(3, TimeUnit.SECONDS)
                .assertValue {
                    var flag = true
                    it.results.forEach { result ->
                        flag = flag
                                && result.adult != null
                                && result.video != null
                                && result.genre_ids != null
                                && result.id != null
                                && result.original_language != null
                                && result.overview != null
                                && result.original_title != null
                                && result.release_date != null
                                && result.popularity != null
                                && result.vote_average != null
                                && result.vote_count != null
                                && result.title != null
                    }
                    flag
                }
                .assertComplete()
        }
    }

    @Test
    fun fetchUpcomingMovies() {
        for(i in 1..3) {
            TheMovieDatabaseProviderImpl().fetchUpcomingMovies(i.toString(), "ru-RU", "RU").toObservable()
                .doOnNext {
                    it.results.forEach {result->
                        Log.d("Test",result.toString())
                    }
                }
                .test()
                .awaitDone(3, TimeUnit.SECONDS)
                .assertValue {
                    var flag = true
                    it.results.forEach { result ->
                        flag = flag
                                && result.adult != null
                                && result.video != null
                                && result.genre_ids != null
                                && result.id != null
                                && result.original_language != null
                                && result.overview != null
                                && result.original_title != null
                                && result.release_date != null
                                && result.popularity != null
                                && result.vote_average != null
                                && result.vote_count != null
                                && result.title != null
                    }
                    flag
                }
                .assertComplete()
        }
    }

    @Test
    fun fetchTopRatedMovies() {
        for(i in 1..300) {
            TheMovieDatabaseProviderImpl().fetchTopRatedMovies(i.toString(), "ru-RU", "RU").toObservable()
                .doOnNext {
                    it.results.forEach {result->
                        Log.d("Test",result.toString())
                    }
                }
                .test()
                .awaitDone(3, TimeUnit.SECONDS)
                .assertValue {
                    var flag = true
                    it.results.forEach { result ->
                        flag = flag
                                && result.adult != null
                                && result.video != null
                                && result.genre_ids != null
                                && result.id != null
                                && result.original_language != null
                                && result.overview != null
                                && result.original_title != null
                                && result.release_date != null
                                && result.popularity != null
                                && result.vote_average != null
                                && result.vote_count != null
                                && result.title != null
                    }
                    flag
                }
                .assertComplete()
        }
    }

    @Test
    fun fetchPopularMovies() {
        for(i in 1..100) {
            TheMovieDatabaseProviderImpl().fetchPopularMovies(i.toString(), "ru-RU", "RU").toObservable()
                .doOnNext {
                    it.results.forEach {result->
                        Log.d("Test",result.toString())
                    }
                }
                .test()
                .awaitDone(3, TimeUnit.SECONDS)
                .assertValue {
                    var flag = true
                    it.results.forEach { result ->
                        flag = flag
                                && result.adult != null
                                && result.video != null
                                && result.genre_ids != null
                                && result.id != null
                                && result.original_language != null
                                && result.overview != null
                                && result.original_title != null
                                && result.release_date != null
                                && result.popularity != null
                                && result.vote_average != null
                                && result.vote_count != null
                                && result.title != null
                    }
                    flag
                }
                .assertComplete()
        }
    }

    @Test
    fun fetchTrendingMovies() {
        TheMovieDatabaseProviderImpl().fetchTrendingMovies("movie", "day").toObservable()
            .doOnNext {
                it.results.forEach {result->
                    Log.d("Test",result.toString())
                }
            }
            .test()
            .awaitDone(3, TimeUnit.SECONDS)
            .assertValue {
                var flag = true
                it.results.forEach { result ->
                    flag = flag
                            && result.adult != null
                            && result.video != null
                            && result.genre_ids != null
                            && result.id != null
                            && result.original_language != null
                            && result.overview != null
                            && result.original_title != null
                            && result.release_date != null
                            && result.popularity != null
                            && result.vote_average != null
                            && result.vote_count != null
                            && result.title != null
                            && result.media_type != null
                }
                flag
            }
            .assertComplete()
    }

    @Test
    fun fetchMovieDetails() {
    }

    @Test
    fun fetchPeopleDetails() {
    }

    @Test
    fun searchMovies() {
        val strings = listOf("Чудо","Люди","Мертвые","Джокер","Большая"," ","Гонка","Жизнь","Лучшие")
        for(i in strings) {
            for(j in 1..10) {
                TheMovieDatabaseProviderImpl().searchMovies(j.toString(),i,"ru-RU","RU").toObservable()
                    .doOnNext {
                        it.results.forEach { result ->
                            Log.d("Test", result.toString())
                        }
                    }
                    .test()
                    .awaitDone(3, TimeUnit.SECONDS)
                    .assertValue {
                        var flag = true
                        it.results.forEach { result ->
                            flag = flag
                                    && result.adult != null
                                    && result.video != null
                                    && result.genre_ids != null
                                    && result.id != null
                                    && result.original_language != null
                                    && result.overview != null
                                    && result.original_title != null
                                    && result.popularity != null
                                    && result.vote_average != null
                                    && result.vote_count != null
                                    && result.title != null
                        }
                        flag
                    }
                    .assertComplete()
            }
        }
    }

    @Test
    fun discoverMovies() {
    }

    @Test
    fun fetchGenres() {
        TheMovieDatabaseProviderImpl().fetchGenres("ru-RU").toObservable()
            .test()
            .awaitDone(3, TimeUnit.SECONDS)
            .assertValue {
                var flag = true
                it.genres.forEach { result ->
                    flag = flag
                            && result.id != null
                            && result.name != null
                }
                flag
            }
            .assertComplete()
    }
}