package com.abdulmanov.MoviCorn.ui.details_person


import com.abdulmanov.domain.models.people.PeopleDetails

interface DetailsPersonContract {

    interface View{
        fun showEmptyProgress(show:Boolean)

        fun showRefreshProgress(show: Boolean)

        fun showError(show:Boolean,error:Throwable? = null )

        fun showData(data: PeopleDetails)

    }

    interface Presenter{
        fun attach(view:View)

        fun detach()

        fun loadData(id:Long,lang:String)

        fun refresh(id:Long,lang:String)

    }
}