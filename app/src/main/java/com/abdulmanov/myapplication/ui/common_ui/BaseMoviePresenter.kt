package com.abdulmanov.myapplication.ui.common_ui

import com.abdulmanov.core.network.dto.movies.MoviesDTO
import com.abdulmanov.myapplication.model.mappers.FilmShortMapper
import com.abdulmanov.myapplication.model.vo.FilmShort
import io.reactivex.Observable

open class BaseMoviePresenter:BaseMovieContract.Presenter<FilmShort> {

    private var moviePaginator: MoviePaginator<FilmShort, MoviesDTO>?=null
    private var mapper: FilmShortMapper = FilmShortMapper()

    override fun attach(view: BaseMovieContract.View<FilmShort>, func: (page: Int) -> Observable<MoviesDTO>) {
        moviePaginator =
            MoviePaginator(view, mapper, func)
    }

    override fun detach() {
        moviePaginator?.release()
        moviePaginator = null
    }

    override fun loadNextPage() {
        moviePaginator?.loadNewPage()
    }

    override fun refresh() {
        moviePaginator?.refresh()
    }

    override fun setFunc(func:(page:Int)->Observable<MoviesDTO>) {
        moviePaginator?.requestFactory = func
        moviePaginator?.refresh()
    }
}