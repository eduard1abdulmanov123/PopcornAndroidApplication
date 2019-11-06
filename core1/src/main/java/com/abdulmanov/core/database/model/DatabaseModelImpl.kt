package com.abdulmanov.core.database.model


import android.content.Context
import com.abdulmanov.core.common.Constant.Database.Companion.LIMIT
import com.abdulmanov.core.database.db.MoviesDataBase
import com.abdulmanov.core.database.db.entity.Movie
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DatabaseModelImpl(context: Context):Model {

    private val db by lazy {
        MoviesDataBase.getDatabase(context)
    }
    private val uiThread by lazy {
        AndroidSchedulers.mainThread()
    }
    private val ioThread: Scheduler by lazy {
        Schedulers.io()
    }

    override fun getMoviesSortTitle(page: Int): Single<List<Movie>> {

        return Single.create<List<Movie>> {
            it.onSuccess(db.movieDao().getMoviesSortTitle(LIMIT, LIMIT*(page-1)))
        }.subscribeOn(ioThread).observeOn(uiThread)
    }

    override fun existsFilm(id: Long):Maybe<Movie>{
        return Maybe.create<Movie> {
            with(db.movieDao().existFilm(id)) {
                if (this != null)
                    it.onSuccess(this)
                else
                    it.onComplete()
            }
        }.subscribeOn(ioThread).observeOn(uiThread)
    }

    override fun insertMovie(movie: Movie):Completable {
        return Completable.fromAction{db.movieDao().insert(movie)}
            .subscribeOn(ioThread)
            .observeOn(uiThread)
    }

    override fun deleteMovie(id: Long):Completable {
        return Completable.fromAction{db.movieDao().delete(id)}
            .subscribeOn(ioThread)
            .observeOn(uiThread)
    }

}