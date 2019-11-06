package com.abdulmanov.MoviCorn.ui.details_person


import com.abdulmanov.domain.repositories.MoviesRepository
import io.reactivex.disposables.Disposable

class DetailsPersonPresenter(private val model:MoviesRepository):DetailsPersonContract.Presenter {

    private var view: DetailsPersonContract.View? = null
    private var requestDisposable: Disposable? = null

    override fun attach(view: DetailsPersonContract.View) {
        this.view = view
    }

    override fun detach() {
        view = null
        requestDisposable?.dispose()
    }

    override fun loadData(id: Long, lang: String) {
        view?.showEmptyProgress(true)
        requestDisposable = model.fetchPeopleDetails(id,lang)
            .subscribe(
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

    override fun refresh(id: Long, lang: String) {
        view?.showRefreshProgress(true)
        requestDisposable = model.fetchPeopleDetails(id, lang)
            .subscribe(
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