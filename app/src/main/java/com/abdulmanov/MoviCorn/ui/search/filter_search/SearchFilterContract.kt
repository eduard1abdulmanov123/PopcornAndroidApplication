package com.abdulmanov.MoviCorn.ui.search.filter_search

import com.abdulmanov.MoviCorn.model.Movie
import com.abdulmanov.MoviCorn.ui.common_ui.MoviePaginator
import com.abdulmanov.domain.models.genres.Genre
import com.abdulmanov.domain.models.movies.MovieMedium
import io.reactivex.Single

interface SearchFilterContract {

    interface View: MoviePaginator.ViewControl<Movie>{
        fun createFilter(genres:List<Genre>)
    }


    interface Presenter{
        fun attach(view: SearchFilterContract.View)

        fun detach()

        fun loadGenres(lang:String)

        fun reloadGenres(lang:String)

        fun loadNextPage()

        fun refresh()

        fun changeRequestFactory(requestFactory: (page: Int) -> Single<List<MovieMedium>>)
    }

}