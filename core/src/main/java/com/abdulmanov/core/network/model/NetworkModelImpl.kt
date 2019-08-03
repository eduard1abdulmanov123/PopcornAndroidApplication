package com.abdulmanov.core.network.model


import android.content.Context
import android.util.Log
import com.abdulmanov.core.common.Constant.Network.Companion.BASE_POSTER_PATH_URL_185
import com.abdulmanov.core.common.Constant.Network.Companion.BASE_POSTER_PATH_URL_550
import com.abdulmanov.core.common.Constant.Thread.Companion.IO_THREAD
import com.abdulmanov.core.common.Constant.Thread.Companion.UI_THREAD
import com.abdulmanov.core.network.api.ApiMoviesInterface
import com.abdulmanov.core.network.api.ApiTrailerInterface
import com.abdulmanov.core.network.common.getProfilePath
import com.abdulmanov.core.network.common.getThumbnail
import com.abdulmanov.core.network.common.init
import com.abdulmanov.core.network.common.toMap
import com.abdulmanov.core.network.di.component.DaggerNetworkComponent
import com.abdulmanov.core.network.di.module.NetworkModule
import com.abdulmanov.core.network.dto.movies.FilmDetailsDTO
import com.abdulmanov.core.network.dto.movies.MoviesDTO
import com.abdulmanov.core.network.dto.movies.VideoDTO
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Named

class NetworkModelImpl(context: Context): Model {

    @Inject
    lateinit var apiMovies: ApiMoviesInterface

    @Inject
    lateinit var apiTrailer: ApiTrailerInterface

    @Inject
    @field:Named(UI_THREAD)
    lateinit var uiThread: Scheduler

    @Inject
    @field:Named(IO_THREAD)
    lateinit var ioThread: Scheduler

    private var genres: Map<Long, String>? = null

    init {
        Log.d("Model","create Network")
        DaggerNetworkComponent.builder().networkModule(NetworkModule(context)).build().inject(this)
    }

    override fun getNowPlayingMovies(
        page: String,
        lang: String,
        reg: String
    ): Observable<MoviesDTO> {
        return responseMovies(lang, BASE_POSTER_PATH_URL_550) {
            apiMovies.getNowPlayingMovies(page, lang, reg)
        }
    }

    override fun getUpcomingMovies(page: String, lang: String, reg: String): Observable<MoviesDTO> {
        return responseMovies(lang, BASE_POSTER_PATH_URL_550) {
            apiMovies.getUpcomingMovies(page, lang, reg)
        }
    }

    override fun getTopRatedMovies(page: String, lang: String, reg: String): Observable<MoviesDTO> {
        return responseMovies(lang, BASE_POSTER_PATH_URL_185) {
            apiMovies.getTopRatedMovies(page, lang, reg)
        }
    }

    override fun getPopularMovies(page: String, lang: String, reg: String): Observable<MoviesDTO> {
        return responseMovies(lang, BASE_POSTER_PATH_URL_185) {
            apiMovies.getPopularMovies(page, lang, reg)
        }
    }

    override fun getSearchMovies(
        page: String,
        lang: String,
        reg: String,
        key: String
    ): Observable<MoviesDTO> {
        return responseMovies(lang, BASE_POSTER_PATH_URL_185) {
            apiMovies.getSearchMovies(page, lang, reg, key)
        }
    }

    override fun getDetailFilm(id: Int, lang: String): Observable<FilmDetailsDTO> {
        val observable = Observable.create<FilmDetailsDTO> { subscriber ->
            val responseDetailFilm = apiMovies.getDetailFilm(id, lang).execute()
            if (responseDetailFilm.isSuccessful) {
                with(responseDetailFilm.body()!!) {
                    val poster = BASE_POSTER_PATH_URL_550 + posterPath
                    val creditsCopy = credits.copy(
                        cast = credits.cast.map { it.copy(profilePath = it.getProfilePath()) },
                        crew = credits.crew.map { it.copy(profilePath = it.getProfilePath()) }
                    )

                    val videosCopy = videos.copy(results = getThumbnailTrailer(videos.results))
                    subscriber.onNext(
                        copy(
                            posterPath = poster,
                            credits = creditsCopy,
                            videos = videosCopy
                        )
                    )
                }
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(responseDetailFilm.message()))
            }
        }
        return observable.compose(applySchedulers())
    }

    private fun getThumbnailTrailer(keys: List<VideoDTO>): List<VideoDTO> {
        val videos = mutableListOf<VideoDTO>()
        keys.filter { it.site == "YouTube" }.forEach {
            val response = apiTrailer.getTrailerInfo(it.path).execute()
            if (response.isSuccessful) {
                videos.add(
                    it.copy(thumbnail = response.body()!!.getThumbnail())
                )
            }
        }
        return videos
    }

    private fun responseMovies(
        lang: String,
        imageUrl: String,
        func: () -> Call<MoviesDTO>
    ): Observable<MoviesDTO> {
        val observable = Observable.create<MoviesDTO> { subscriber ->
            val resMovies = func().execute()
            initializeGenres(lang)
            if (resMovies.isSuccessful && genres != null) {
                val data = resMovies.body()!!
                subscriber.onNext(data.copy(
                    results = data.results.map { it.init(imageUrl, genres!!) }
                ))
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(resMovies.message()))
            }
        }
        return observable.compose(applySchedulers())
    }

    private fun initializeGenres(language: String) {
        if (genres == null) {
            val response = apiMovies.getGenres(language).execute()
            if (response.isSuccessful) {
                genres = response.body()?.toMap()
            }
        }
    }

    private fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(ioThread)
                .observeOn(uiThread)
                .unsubscribeOn(ioThread)
        }
    }
}