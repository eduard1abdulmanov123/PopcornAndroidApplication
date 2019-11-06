package com.abdulmanov.MoviCorn.ui.search.filter_search

import com.abdulmanov.MoviCorn.model.Movie
import com.abdulmanov.MoviCorn.ui.common_ui.MoviePaginator
import com.abdulmanov.domain.models.movies.MovieMedium
import com.abdulmanov.domain.repositories.MoviesRepository
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function

class SearchFilterPresenter(
    private val moviesRepository: MoviesRepository
): SearchFilterContract.Presenter {

    private var movieMediumPaginator: MoviePaginator<Movie, MovieMedium>?=null
    private var requestDisposable: Disposable? = null
    private var view:SearchFilterContract.View?=null

    override fun attach(view: SearchFilterContract.View) {
        this.view = view
    }

    override fun detach() {
        this.view = null
        movieMediumPaginator?.release()
        movieMediumPaginator = null
        requestDisposable?.dispose()
    }

    override fun loadGenres(lang:String) {
        view?.showEmptyProgress(true)
        requestDisposable = moviesRepository.fetchGenres(lang).subscribe(
            {
                view?.showEmptyProgress(false)
                view?.createFilter(it)
            },
            {
                view?.showEmptyProgress(false)
                view?.showEmptyError(true)
            }
        )
    }

    override fun reloadGenres(lang: String) {
        view?.showFailProgress(true)
        requestDisposable = moviesRepository.fetchGenres(lang).subscribe(
            {
                view?.showFailProgress(false)
                view?.createFilter(it)
                view?.showEmptyError(false)
            },
            {
                view?.showFailProgress(false)
            }
        )
    }

    override fun loadNextPage() {
        movieMediumPaginator?.loadNewPage()
    }

    override fun refresh() {
        movieMediumPaginator?.refresh()
    }

    override fun changeRequestFactory(requestFactory: (page: Int) -> Single<List<MovieMedium>>) {
        if(movieMediumPaginator==null){
            movieMediumPaginator = MoviePaginator(
                view as MoviePaginator.ViewControl<Movie>,
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
        }else {
            movieMediumPaginator?.requestFactory = requestFactory
        }
        movieMediumPaginator?.refresh()
    }
}