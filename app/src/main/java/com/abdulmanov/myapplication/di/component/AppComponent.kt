package com.abdulmanov.myapplication.di.component

import com.abdulmanov.myapplication.di.module.ActivityModule
import com.abdulmanov.myapplication.di.module.AppModule
import com.abdulmanov.myapplication.di.module.FragmentModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun fragmentComponent(fragmentModule: FragmentModule):FragmentComponent

    fun activityComponent(activityModule:ActivityModule):ActivityComponent
}