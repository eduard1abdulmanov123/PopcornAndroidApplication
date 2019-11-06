package com.abdulmanov.MoviCorn.ui.library.library_main

import com.abdulmanov.MoviCorn.model.Movie



interface LibraryContract {

    interface View{
        fun showProgress(show:Boolean)

        fun showEmptyData(show:Boolean)

        fun showData(movies:List<Movie>)
    }

    interface Presenter{
        fun attach(view: View)

        fun detach()

        fun loadData()
    }
}