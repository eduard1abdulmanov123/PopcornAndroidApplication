package com.abdulmanov.core.database.model


import android.content.Context
import android.util.Log
import com.abdulmanov.core.common.Constant.Database.Companion.LIMIT
import com.abdulmanov.core.common.Constant.Thread.Companion.IO_THREAD
import com.abdulmanov.core.common.Constant.Thread.Companion.UI_THREAD
import com.abdulmanov.core.database.db.MoviesDataBase
import com.abdulmanov.core.database.db.entity.Movie
import com.abdulmanov.core.database.di.component.DaggerDatabaseComponent
import com.abdulmanov.core.database.di.module.DatabaseModule
import io.reactivex.*
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Named


class DatabaseModelImpl(context: Context):Model {

    @Inject
    lateinit var db: MoviesDataBase

    @Inject
    @field:Named(UI_THREAD)
    lateinit var uiThread: Scheduler

    @Inject
    @field:Named(IO_THREAD)
    lateinit var ioThread: Scheduler

    //var dispose:Disposable?=null
    init {
        Log.d("Model","create Database")
        DaggerDatabaseComponent.builder().databaseModule(DatabaseModule(context)).build()
            .inject(this)
    }

    override fun getMoviesSortTitle(page: Int): Flowable<List<Movie>> {
        return db.movieDao().getMoviesSortTitle(LIMIT, LIMIT*(page-1))
            .subscribeOn(ioThread)
            .observeOn(uiThread)
    }

    override fun existsFilm(id: Long):Maybe<Movie>{
        return db.movieDao().existFilm(id)
            .subscribeOn(ioThread)
            .observeOn(uiThread)
    }

    override fun insertMovie(movie: Movie):Completable {
        /*dispose = db.movieDao().getMoviesSortTitle(200,0).subscribe(){
            Log.d("DBMOVIE",it.size.toString())
            //Log.d("DBMOVIE","-")
            dispose?.dispose()
        }*/
        return Completable.fromAction{db.movieDao().insert(movie)}
            .subscribeOn(ioThread)
            .observeOn(uiThread)
    }

    override fun deleteMovie(id: Long):Completable {
       /* dispose=db.movieDao().getMoviesSortTitle(200,0).subscribe(){
            Log.d("DBMOVIE",it.size.toString())
            //Log.d("DBMOVIE","-")
            dispose?.dispose()
        }*/
        return Completable.fromAction{db.movieDao().delete(id)}
            .subscribeOn(ioThread)
            .observeOn(uiThread)
    }

}