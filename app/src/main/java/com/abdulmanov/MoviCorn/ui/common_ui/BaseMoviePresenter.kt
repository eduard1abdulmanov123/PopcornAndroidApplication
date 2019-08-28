package com.abdulmanov.MoviCorn.ui.common_ui

import com.abdulmanov.core.network.dto.movies.MoviesDTO
import com.abdulmanov.MoviCorn.model.mappers.movies.MoviesDTOtoListFilmMediumMapper
import com.abdulmanov.MoviCorn.model.vo.movie.FilmMedium
import io.reactivex.Single

open class BaseMoviePresenter:BaseListMovieContract.Presenter<FilmMedium> {

    private var mMoviePaginator: MoviePaginator<FilmMedium, MoviesDTO>?=null
    private var mMapperMoviesDTOtoListMedium: MoviesDTOtoListFilmMediumMapper =
        MoviesDTOtoListFilmMediumMapper()

    override fun attach(view: BaseListMovieContract.View<FilmMedium>, requestFactory: (page: Int) -> Single<MoviesDTO>) {
        mMoviePaginator =
            MoviePaginator(view, mMapperMoviesDTOtoListMedium, requestFactory)
    }

    override fun detach() {
        mMoviePaginator?.release()
        mMoviePaginator = null
    }

    override fun loadNextPage() {
        mMoviePaginator?.loadNewPage()
    }

    override fun refresh() {
        mMoviePaginator?.refresh()
    }

    override fun changeRequestFactory(func:(page:Int)-> Single<MoviesDTO>) {
        mMoviePaginator?.requestFactory = func
        mMoviePaginator?.refresh()
    }
}