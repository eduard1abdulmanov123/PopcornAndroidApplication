package com.abdulmanov.MoviCorn.ui.library

import android.util.Log
import com.abdulmanov.core.database.db.entity.Movie
import com.abdulmanov.core.database.model.Model
import com.abdulmanov.MoviCorn.model.mappers.FilmLibraryMapper
import com.abdulmanov.MoviCorn.model.vo.FilmLibrary
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable

class LibraryPresenter:LibraryContract.Presenter<FilmLibrary> {

    private val mapper = FilmLibraryMapper()
    private var libraryPaginator: LibraryPaginator<FilmLibrary, Movie>? = null
    private var view: LibraryContract.View<FilmLibrary>? = null
    private var requestDisposable: Disposable? = null

    override fun attach(
        view: LibraryContract.View<FilmLibrary>,
        func: (page: Int) -> Flowable<List<Movie>>
    ) {
        this.view = view
        libraryPaginator = LibraryPaginator(view, mapper, func)
    }

    override fun detach() {
        libraryPaginator?.release()
        libraryPaginator = null
        view = null
        requestDisposable?.dispose()
    }

    override fun loadNewPage() {
        libraryPaginator?.loadNewPage()
    }

    override fun refresh() {
        libraryPaginator?.refresh()
    }

    override fun setFunc(func: (page: Int) -> Flowable<List<Movie>>) {
        libraryPaginator?.requestFactory = func
        libraryPaginator?.refresh()
    }

    override fun deleteFilm(database: Model, film: FilmLibrary) {
        requestDisposable = database.deleteMovie(film.id).subscribe (
            { view?.showDeleteFilm(film) },
            { Log.d("LibraryFragmentLog","Ошибка с удалением")}
        )
    }
}