package com.abdulmanov.MoviCorn.ui.library.library_main

import com.abdulmanov.MoviCorn.model.Movie
import com.abdulmanov.domain.repositories.MoviesDbRepository
import io.reactivex.disposables.Disposable

class LibraryPresenter( private val database: MoviesDbRepository):
    LibraryContract.Presenter {

    private var view: LibraryContract.View? = null
    private var requestDisposable: Disposable? = null

    override fun attach(view: LibraryContract.View) {
        this.view = view
    }

    override fun detach() {
        this.view = null
        requestDisposable?.dispose()
    }

    override fun loadData() {
        view?.showProgress(true)
        requestDisposable = database.fetchMovies()
            .map {  movies->
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
            }
            .subscribe(
                {
                    view?.showProgress(false)
                    if(it.isEmpty()){
                        view?.showEmptyData(true)
                    }else{
                        view?.showEmptyData(false)
                        view?.showData(it)
                    }
                },
                {

                }
            )
    }
}