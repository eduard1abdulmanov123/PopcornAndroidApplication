package com.abdulmanov.MoviCorn.ui.library.library_action

import android.util.Log
import com.abdulmanov.MoviCorn.model.Movie
import com.abdulmanov.domain.repositories.MoviesDbRepository
import io.reactivex.disposables.Disposable

class LibraryActionPresenter(private val database: MoviesDbRepository):LibraryActionContract.Presenter {

    private var view: LibraryActionContract.View? = null
    private var requestDisposable: Disposable? = null

    override fun attach(view: LibraryActionContract.View) {
       this.view = view
    }

    override fun detach() {
        this.view = null
        requestDisposable?.dispose()
    }

    override fun deleteMovies(movies: List<Movie>) {
        //Log.d("LibraryActionTesting",movies.toString())
        requestDisposable = database.deleteMovies(movies.map { it.id }).subscribe(
            {
                view?.callbackDelete()
            },
            {

            }
        )
    }
}