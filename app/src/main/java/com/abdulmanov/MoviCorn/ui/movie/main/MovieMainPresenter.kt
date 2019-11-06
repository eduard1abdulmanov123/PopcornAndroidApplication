package com.abdulmanov.MoviCorn.ui.movie.main


import com.abdulmanov.domain.repositories.MoviesRepository
import io.reactivex.disposables.Disposable

class MovieMainPresenter(private val model:MoviesRepository):MovieMainContract.Presenter {

    private var view: MovieMainContract.View? = null
    private var requestDisposable: Disposable? = null

    override fun attach(view: MovieMainContract.View) {
        this.view = view
    }

    override fun detach() {
        view = null
        requestDisposable?.dispose()
    }

    override fun loadData(lang:String,reg:String) {
        view?.showEmptyProgress(true)
        requestDisposable =model.fetchPackageMovies(lang,reg).subscribe(
            {
                view?.showData(it)
                view?.showEmptyProgress(false)
            },
            {
                view?.showEmptyProgress(false)
                view?.showError(true,it)
            }
        )
    }

    override fun refresh(lang:String,reg:String) {
        view?.showRefreshProgress(true)
        requestDisposable =model.fetchPackageMovies(lang,reg).subscribe(
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