package com.abdulmanov.core.network.model


import android.content.Context
import com.abdulmanov.core.common.Constant.Network.Companion.MOVIES_BASE_URL
import com.abdulmanov.core.network.api.ApiModule
import com.abdulmanov.core.common.setupGenres
import com.abdulmanov.core.common.toMap
import com.abdulmanov.core.network.dto.people.PeopleDetailsDTO
import com.abdulmanov.core.network.dto.movies.MoviesDetailsDTO
import com.abdulmanov.core.network.dto.movies.MoviesDTO
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call

class NetworkModelImpl(context: Context): Model {

    private val apiMovies by lazy {
        ApiModule.getApiMoviesInterface(context, MOVIES_BASE_URL)
    }
    private val uiThread by lazy {
        AndroidSchedulers.mainThread()
    }
    private val ioThread: Scheduler by lazy {
        Schedulers.io()
    }

    private var genresMap: Map<Long, String>? = null

    override fun getNowPlayingMovies(
        page: String,
        lang: String,
        reg: String
    ): Single<MoviesDTO> {
        return responseMovies(lang) {
            apiMovies.getNowPlayingMovies(page, lang, reg)
        }
    }

    override fun getUpcomingMovies(page: String, lang: String, reg: String): Single<MoviesDTO> {
        return responseMovies(lang) {
            apiMovies.getUpcomingMovies(page, lang, reg)
        }
    }

    override fun getTopRatedMovies(page: String, lang: String, reg: String): Single<MoviesDTO> {
        return responseMovies(lang) {
            apiMovies.getTopRatedMovies(page, lang, reg)
        }
    }

    override fun getPopularMovies(page: String, lang: String, reg: String): Single<MoviesDTO> {
        return responseMovies(lang) {
            apiMovies.getPopularMovies(page, lang, reg)
        }
    }

    override fun getSearchMovies(
        page: String,
        lang: String,
        reg: String,
        key: String
    ): Single<MoviesDTO> {
        return responseMovies(lang) {
            apiMovies.getSearchMovies(page, lang, reg, key)
        }
    }

    override fun getDetailFilm(id: Long, lang: String): Single<MoviesDetailsDTO> {
        val single = Single.create<MoviesDetailsDTO> { emitter ->
            val responseDetailFilm = apiMovies.getDetailFilm(id, lang).execute()
            initializeGenres(lang)
            if (responseDetailFilm.isSuccessful && genresMap != null) {
                with(responseDetailFilm.body()!!) {
                    emitter.onSuccess(copy(similar = similar.setupGenres(genresMap!!)))
                }
            } else {
                emitter.onError(Throwable(responseDetailFilm.message()))
            }
        }
        return single.subscribeOn(ioThread).observeOn(uiThread)
    }

    override fun getPeopleDetails(id: Long, lang: String): Single<PeopleDetailsDTO> {
        val single = Single.create<PeopleDetailsDTO> { emitter ->
            val responseDetailCredit = apiMovies.getDetailCredit(id,lang).execute()
            initializeGenres(lang)
            if (responseDetailCredit.isSuccessful && genresMap != null) {
                with(responseDetailCredit.body()!!) {
                    val filmography = movieCredit.copy(
                        castPeople = movieCredit.castPeople.map { it.setupGenres(genresMap!!)},
                        crewPeople = movieCredit.crewPeople.map { it.setupGenres(genresMap!!)}
                    )
                    emitter.onSuccess(copy(movieCredit = filmography))
                }
            } else {
                emitter.onError(Throwable(responseDetailCredit.message()))
            }
        }
        return single.subscribeOn(ioThread).observeOn(uiThread)
    }

    private fun responseMovies(lang: String, func: () -> Call<MoviesDTO>): Single<MoviesDTO> {
        val single = Single.create<MoviesDTO> { emitter ->
            val responseMovies = func().execute()
            initializeGenres(lang)
            if (responseMovies.isSuccessful && genresMap != null) {
                with(responseMovies.body()!!) {
                    emitter.onSuccess(setupGenres(genresMap!!))
                }
            } else {
                emitter.onError(Throwable(responseMovies.message()))
            }
        }
        return single.subscribeOn(ioThread).observeOn(uiThread)
    }

    private fun initializeGenres(language: String) {
        if (genresMap == null) {
            val response = apiMovies.getGenres(language).execute()
            if (response.isSuccessful) {
                genresMap = response.body()?.toMap()
            }
        }
    }
}