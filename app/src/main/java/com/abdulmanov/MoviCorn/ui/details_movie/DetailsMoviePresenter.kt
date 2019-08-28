package com.abdulmanov.MoviCorn.ui.details_movie
import com.abdulmanov.core.database.db.entity.Movie
import com.abdulmanov.MoviCorn.R
import com.abdulmanov.core.network.model.Model as NetModel
import com.abdulmanov.core.database.model.Model as DBModel
import com.abdulmanov.MoviCorn.model.mappers.movies.MoviesDetailsDTOtoDetailsMovieMapper
import com.abdulmanov.MoviCorn.model.vo.movie.DetailsMovie
import io.reactivex.disposables.Disposable


class DetailsMoviePresenter(
    private val network: NetModel,
    private val database:DBModel,
    private val movieMapper:MoviesDetailsDTOtoDetailsMovieMapper
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

    override fun saveMovieInLibrary(movie: DetailsMovie) {
        requestDisposable = database.existsFilm(movie.id).subscribe(
            {
                database.deleteMovie(movie.id).subscribe {
                    view?.showMessage(R.string.details_delete_film)
                    view?.showSaved(false)
                }
            },
            {},
            {
                val movie = Movie(
                    movie.id,
                    movie.title,
                    movie.overview,
                    movie.releaseData,
                    movie.genres,
                    movie.posterPath,
                    movie.voteCount,
                    movie.voteAverage
                )
                database.insertMovie(movie).subscribe {
                    view?.showMessage(R.string.details_save_film)
                    view?.showSaved(true)
                }
            }
        )
    }

    override fun loadData(id: Long, lang: String) {
        view?.showEmptyProgress(true)
        loadDetailsMovie(
            id,
            lang,
            {
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
        loadDetailsMovie(
            id,
            lang,
            {
                view?.showRefreshProgress(false)
                view?.showError(false)
            },
            {
                view?.showRefreshProgress(false)
            }
        )
    }

    private fun loadDetailsMovie(id:Long,lang:String,onSuccess: () -> Unit, onError:(error:Throwable?)->Unit){
        requestDisposable = network.getDetailFilm(id,lang)
            .map(movieMapper)
            .subscribe(
                {
                    checkExistenceMovieInTheDB(id,it,onSuccess)
                },
                {
                    onError.invoke(it)
                }
            )
    }

    private fun checkExistenceMovieInTheDB(
        id: Long,
        detailsMovie: DetailsMovie,
        onSuccess: () -> Unit
    ) {
        requestDisposable = database.existsFilm(id).subscribe(
            {
                detailsMovie.existsInTheDB = true
                view?.showData(detailsMovie)
                onSuccess.invoke()
            },
            {},
            {
                view?.showData(detailsMovie)
                onSuccess.invoke()
            }
        )
    }
}