package com.abdulmanov.MoviCorn.ui.search.string_search


import com.abdulmanov.MoviCorn.model.Movie
import com.abdulmanov.MoviCorn.ui.common_ui.MoviePaginator
import com.abdulmanov.domain.models.movies.MovieMedium
import io.reactivex.Single

interface StringSearchContract {

    interface View: MoviePaginator.ViewControl<Movie>

    interface Presenter{
        fun attach(view:View,requestFactory: (page: Int) -> Single<List<MovieMedium>>)

        fun detach()

        fun loadNextPage()

        fun refresh()

        fun changeRequestFactory(requestFactory: (page: Int) -> Single<List<MovieMedium>>)
    }
}