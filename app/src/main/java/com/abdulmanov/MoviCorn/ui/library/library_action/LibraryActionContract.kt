package com.abdulmanov.MoviCorn.ui.library.library_action

import com.abdulmanov.MoviCorn.model.Movie

interface LibraryActionContract {

    interface View{
        fun showMessage(message:Int)

        fun callbackDelete()

        fun finish()
    }

    interface Presenter{

        fun attach(view:View)

        fun detach()

        fun deleteMovies(movies:List<Movie>)
    }
}