package com.abdulmanov.myapplication.ui.library

import com.abdulmanov.core.database.db.entity.Movie
import com.abdulmanov.core.database.model.Model
import com.abdulmanov.myapplication.model.vo.FilmLibrary
import io.reactivex.Flowable

interface LibraryContract {

    interface View<T>:LibraryPaginator.LibraryViewController<T>{
        fun showDeleteFilm(film:FilmLibrary)
    }

    interface Presenter<T>{
        fun attach(view:View<T>,func:(page:Int)->Flowable<List<Movie>>)

        fun detach()

        fun loadNewPage()

        fun refresh()

        fun setFunc(func:(page:Int)->Flowable<List<Movie>>)

        fun deleteFilm(database: Model, film:FilmLibrary)
    }
}