package com.abdulmanov.MoviCorn.ui.details_movie

import com.abdulmanov.MoviCorn.R
import com.abdulmanov.domain.models.movies.MovieDetails
import com.abdulmanov.domain.repositories.MoviesDbRepository
import com.abdulmanov.domain.repositories.MoviesRepository
import io.reactivex.disposables.Disposable


class DetailsMoviePresenter(
    private val network: MoviesRepository,
    private val database:MoviesDbRepository
): DetailsMovieContract.Presenter {

    private var view: DetailsMovieContract.View? = null
    private var requestDisposable: Disposable? = null

    override fun attach(view: DetailsMovieContract.View) {
        this.view = view
    }

    override fun detach() {
        view = null
        requestDisposable?.dispose()
    }

    override fun saveMovieInLibrary(movie: MovieDetails) {
        requestDisposable = database.existsMovie(movie.id).subscribe(
            {
                if(it.isEmpty()){
                    database.insertMovie(movie).subscribe{
                        view?.showMessage(R.string.details_save_film)
                        view?.showSaved(true)
                    }
                }else{
                    database.deleteMovie(movie.id).subscribe{
                        view?.showMessage(R.string.details_delete_film)
                        view?.showSaved(false)
                    }
                }
            },
            {

            }
        )
    }

    override fun loadData(id: Long, lang: String) {
        view?.showEmptyProgress(true)
        requestDisposable = network.fetchMovieDetails(id,lang).subscribe(
            {
                view?.showData(it)
                view?.showEmptyProgress(false)
            },
            {
                view?.showEmptyProgress(false)
                view?.showError(true, it)
            }
        )
    }

    override fun refresh(id: Long, lang: String) {
        view?.showRefreshProgress(true)
        requestDisposable = network.fetchMovieDetails(id,lang).subscribe(
            {
                view?.showData(it)
                view?.showRefreshProgress(false)
                view?.showError(false)
            },
            {
                view?.showRefreshProgress(false)
            }
        )
    }
}