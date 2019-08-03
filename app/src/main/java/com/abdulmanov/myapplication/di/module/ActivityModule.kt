package com.abdulmanov.myapplication.di.module

import com.abdulmanov.core.network.model.Model as NetModel
import com.abdulmanov.core.database.model.Model as DBModel
import com.abdulmanov.myapplication.di.scope.ActivityScope
import com.abdulmanov.myapplication.ui.details.DetailsContract
import com.abdulmanov.myapplication.ui.details.DetailsPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

    @ActivityScope
    @Provides
    fun provideDetailsPresenter(network:NetModel,database:DBModel):DetailsContract.Presenter{
        return DetailsPresenter(network,database)
    }
}