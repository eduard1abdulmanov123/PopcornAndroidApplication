package com.abdulmanov.MoviCorn.ui.movie.list

import com.abdulmanov.MoviCorn.model.Movie
import com.abdulmanov.MoviCorn.ui.common_ui.MoviePaginator
import com.abdulmanov.domain.models.movies.MovieMedium
import io.reactivex.Single

interface MovieListContract {

    interface View : MoviePaginator.ViewControl<Movie>

    interface Presenter{
        fun attach(view:View, requestFactory: (page: Int) -> Single<List<MovieMedium>>)

        fun detach()

        fun loadNextPage()

        fun refresh()
    }
}