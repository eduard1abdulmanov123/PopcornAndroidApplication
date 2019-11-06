package com.abdulmanov.MoviCorn.di.component

import com.abdulmanov.MoviCorn.di.module.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,CoreModule::class,ConvertersModule::class,RepositoriesModule::class])
interface AppComponent {

    fun fragmentComponent(fragmentModule: FragmentModule):FragmentComponent

    fun activityComponent(activityModule:ActivityModule):ActivityComponent
}