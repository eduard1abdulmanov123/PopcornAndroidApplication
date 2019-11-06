package com.abdulmanov.MoviCorn.ui.details_movie


import com.abdulmanov.domain.models.movies.MovieDetails

interface DetailsMovieContract {

    interface View{
        fun showEmptyProgress(show:Boolean)

        fun showRefreshProgress(show:Boolean)

        fun showError(show:Boolean, error:Throwable?=null)

        fun showMessage(stringId:Int)

        fun showSaved(show:Boolean)

        fun showData(data: MovieDetails)
    }

    interface Presenter{
        fun attach(view:View)

        fun detach()

        fun saveMovieInLibrary(movie: MovieDetails)

        fun loadData(id:Long,lang:String)

        fun refresh(id:Long,lang:String)
    }
}