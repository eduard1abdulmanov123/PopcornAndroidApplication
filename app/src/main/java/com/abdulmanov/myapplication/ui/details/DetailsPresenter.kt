package com.abdulmanov.myapplication.ui.details
import com.abdulmanov.core.database.db.entity.Movie
import com.abdulmanov.myapplication.R
import com.abdulmanov.core.network.model.Model as NetModel
import com.abdulmanov.core.database.model.Model as DBModel
import com.abdulmanov.myapplication.model.mappers.FilmDetailsMapper
import com.abdulmanov.myapplication.model.vo.FilmDetails
import com.abdulmanov.myapplication.model.vo.FilmShort
import io.reactivex.disposables.Disposable


class DetailsPresenter(
    private val network: NetModel,
    private val database:DBModel
): DetailsContract.Presenter {

    private var view:DetailsContract.View? = null
    private var mapper:FilmDetailsMapper?=null
    private var requestDisposable: Disposable? = null

    override fun attach(view: DetailsContract.View,mapper: FilmDetailsMapper) {
        this.view = view
        this.mapper = mapper
    }

    override fun detach() {
        view = null
        mapper = null
        requestDisposable?.dispose()
    }

    override fun addFilmInLibrary(film: FilmDetails) {
        val movie = Movie(
            film.id,
            film.title,
            film.overview,
            film.releaseData,
            film.genres.joinToString(", "),
            film.posterPath
        )
        requestDisposable = database.insertMovie(movie)
            .subscribe(
                {
                    view?.showMessage(view?.getContext()!!.getString(R.string.details_added_film))
                    view?.showAddedButton(false)
                },
                { view?.showMessage(it.message.toString()) }
            )
    }

    override fun loadData(id: Long, lang:String) {
        view?.showEmptyProgress(true)
        requestDisposable = network.getDetailFilm(id.toInt(),lang)
            .map(mapper)
            .subscribe(
            {
                view?.showData(it)
                requestDisposable = database.existsFilm(id).subscribe(
                    {view?.showEmptyProgress(false)},
                    {throwable->
                        view?.showEmptyProgress(false)
                        view?.showMessage(throwable.message.toString())
                    },
                    {
                        view?.showAddedButton(true)
                        view?.showEmptyProgress(false)
                    }
                )
            },
            {
                view?.showEmptyProgress(false)
                view?.showErrorMessage(true,it)
            }
        )
    }

    override fun refresh(id: Long, lang: String) {
        view?.showRefreshProgress(true)
        requestDisposable = network.getDetailFilm(id.toInt(),lang)
            .map(mapper)
            .subscribe(
                {
                    view?.showData(it)
                    requestDisposable = database.existsFilm(id).subscribe(
                        {
                            view?.showRefreshProgress(false)
                            view?.showErrorMessage(false)
                        },
                        {throwable->
                            view?.showEmptyProgress(false)
                            view?.showMessage(throwable.message.toString())
                        },
                        {
                            view?.showAddedButton(true)
                            view?.showRefreshProgress(false)
                            view?.showErrorMessage(false)
                        }
                    )
                },
                {
                    view?.showRefreshProgress(false)
                }
            )
    }

}