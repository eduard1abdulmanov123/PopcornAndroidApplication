package com.abdulmanov.MoviCorn.ui.details_movie

import com.abdulmanov.MoviCorn.model.mappers.movies.MoviesDetailsDTOtoDetailsMovieMapper
import com.abdulmanov.MoviCorn.model.vo.movie.DetailsMovie

interface DetailsMovieContract {

    interface View{
        fun showEmptyProgress(show:Boolean)

        fun showRefreshProgress(show:Boolean)

        fun showError(show:Boolean, error:Throwable?=null)

        fun showMessage(stringId:Int)

        fun showSaved(show:Boolean)

        fun showData(data: DetailsMovie)
    }

    interface Presenter{
        fun attach(view:View)

        fun detach()

        fun saveMovieInLibrary(movie: DetailsMovie)

        fun loadData(id:Long,lang:String)

        fun refresh(id:Long,lang:String)
    }
}