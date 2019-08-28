package com.abdulmanov.MoviCorn.ui.details_person

import com.abdulmanov.MoviCorn.model.mappers.person.PeopleDetailsDTOtoDetailsPersonMapper
import com.abdulmanov.MoviCorn.model.vo.person.DetailsPerson

interface DetailsPersonContract {

    interface View{
        fun showEmptyProgress(show:Boolean)

        fun showRefreshProgress(show: Boolean)

        fun showError(show:Boolean,error:Throwable? = null )

        fun showData(data: DetailsPerson)

    }

    interface Presenter{
        fun attach(view:View)

        fun detach()

        fun loadData(id:Long,lang:String)

        fun refresh(id:Long,lang:String)

    }
}