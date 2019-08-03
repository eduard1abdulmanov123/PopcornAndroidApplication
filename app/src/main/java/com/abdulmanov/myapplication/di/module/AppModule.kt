package com.abdulmanov.myapplication.di.module

import android.content.Context
import com.abdulmanov.core.database.model.DatabaseModelImpl
import com.abdulmanov.core.network.model.Model as NetModel
import com.abdulmanov.core.database.model.Model as DBModel
import com.abdulmanov.core.network.model.NetworkModelImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context:Context) {

    @Singleton
    @Provides
    fun provideNetworkModel():NetModel{
        return NetworkModelImpl(context)
    }

    @Singleton
    @Provides
    fun provideDatabaseModel():DBModel{
        return DatabaseModelImpl(context)
    }
}