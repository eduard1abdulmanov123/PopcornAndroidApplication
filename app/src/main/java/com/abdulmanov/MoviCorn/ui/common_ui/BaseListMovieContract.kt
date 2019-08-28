package com.abdulmanov.MoviCorn.ui.common_ui

import com.abdulmanov.core.network.dto.movies.MoviesDTO
import io.reactivex.Single

interface BaseListMovieContract {

    interface View<T> : MoviePaginator.ViewControl<T>

    interface Presenter<T> {
        fun attach(view: View<T>, requestFactory: (page: Int) -> Single<MoviesDTO>)

        fun detach()

        fun loadNextPage()

        fun refresh()

        fun changeRequestFactory(func: (page: Int) -> Single<MoviesDTO>)
    }
}