package com.abdulmanov.MoviCorn

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.abdulmanov.MoviCorn.di.component.AppComponent
import com.abdulmanov.MoviCorn.di.component.DaggerAppComponent
import com.abdulmanov.MoviCorn.di.module.AppModule
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

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}