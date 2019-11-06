package com.abdulmanov.MoviCorn.ui.search.string_search

import com.abdulmanov.MoviCorn.model.Movie
import com.abdulmanov.MoviCorn.ui.common_ui.MoviePaginator
import com.abdulmanov.domain.models.movies.MovieMedium
import io.reactivex.Single
import io.reactivex.functions.Function

class StringSearchPresenter: StringSearchContract.Presenter {

    private var movieMediumPaginator: MoviePaginator<Movie, MovieMedium>?=null

    override fun attach(view: StringSearchContract.View, requestFactory: (page: Int) -> Single<List<MovieMedium>>) {
        movieMediumPaginator = MoviePaginator(
            view,
            Function {movies->
                movies.map {
                    Movie(
                        it.id,
                        it.voteAverage,
                        it.voteCount,
                        it.title,
                        it.posterPath,
                        it.overview,
                        it.releaseDate,
                        it.genres
                    )
                }
            },
            requestFactory
        )
    }

    override fun detach() {
        movieMediumPaginator?.release()
        movieMediumPaginator = null
    }

    override fun loadNextPage() {
        movieMediumPaginator?.loadNewPage()
    }

    override fun refresh() {
        movieMediumPaginator?.refresh()
    }

    override fun changeRequestFactory(requestFactory: (page: Int) -> Single<List<MovieMedium>>) {
        movieMediumPaginator?.requestFactory = requestFactory
        movieMediumPaginator?.refresh()
    }
}