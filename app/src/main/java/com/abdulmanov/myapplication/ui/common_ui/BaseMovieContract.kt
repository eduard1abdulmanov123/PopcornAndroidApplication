package com.abdulmanov.myapplication.ui.common_ui

import com.abdulmanov.core.network.dto.movies.MoviesDTO
import io.reactivex.Observable

interface BaseMovieContract {

    interface View<T>:MoviePaginator.ViewControl<T>

    interface Presenter<T> {
        fun attach(view: BaseMovieContract.View<T>, func: (page: Int) -> Observable<MoviesDTO>)

        fun detach()

        fun loadNextPage()

        fun refresh()

        fun setFunc(func:(page:Int)->Observable<MoviesDTO>)
    }
}