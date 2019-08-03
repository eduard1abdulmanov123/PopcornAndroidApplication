package com.abdulmanov.myapplication.ui.details

import android.content.Context
import com.abdulmanov.myapplication.model.mappers.FilmDetailsMapper
import com.abdulmanov.myapplication.model.vo.FilmDetails
import com.abdulmanov.myapplication.model.vo.FilmShort

interface DetailsContract {

    interface View{

        fun showEmptyProgress(show:Boolean)

        fun showRefreshProgress(show:Boolean)

        fun showErrorMessage(show:Boolean,error:Throwable?=null)

        fun showMessage(str:String)

        fun showData(data:FilmDetails)

        fun showAddedButton(show:Boolean)

        fun getContext(): Context
    }

    interface Presenter{
        fun attach(view:DetailsContract.View,mapper: FilmDetailsMapper)

        fun detach()

        fun addFilmInLibrary(film:FilmDetails)

        fun loadData(id:Long,lang:String)

        fun refresh(id:Long,lang:String)
    }
}