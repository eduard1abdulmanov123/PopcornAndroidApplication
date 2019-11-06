package com.abdulmanov.MoviCorn.ui.movie.main

import com.abdulmanov.domain.models.movies.PackageMovies

interface MovieMainContract {

    interface View{
        fun showEmptyProgress(show:Boolean)

        fun showRefreshProgress(show:Boolean)

        fun showError(show:Boolean, error:Throwable?=null)

        fun showData(data: PackageMovies)
    }

    interface Presenter{
        fun attach(view:View)

        fun detach()

        fun loadData(lang:String,reg:String)

        fun refresh(lang:String,reg:String)
    }

}