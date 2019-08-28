package com.abdulmanov.MoviCorn.di.component

import com.abdulmanov.MoviCorn.di.module.ActivityModule
import com.abdulmanov.MoviCorn.di.module.AppModule
import com.abdulmanov.MoviCorn.di.module.FragmentModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun fragmentComponent(fragmentModule: FragmentModule):FragmentComponent

    fun activityComponent(activityModule:ActivityModule):ActivityComponent
}