package com.abdulmanov.myapplication

import android.app.Application
import com.abdulmanov.myapplication.di.component.AppComponent
import com.abdulmanov.myapplication.di.component.DaggerAppComponent
import com.abdulmanov.myapplication.di.module.AppModule
import io.reactivex.plugins.RxJavaPlugins



class BaseApp:Application() {

    companion object {
        lateinit var instance: BaseApp private set
    }

    lateinit var appComponent:AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
        RxJavaPlugins.setErrorHandler { }

    }

}