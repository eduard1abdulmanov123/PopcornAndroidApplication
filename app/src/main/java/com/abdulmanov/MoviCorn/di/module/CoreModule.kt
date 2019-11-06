package com.abdulmanov.MoviCorn.di.module

import android.content.Context
import com.abdulmanov.core.remote.providers.TheMovieDatabaseProvider
import com.abdulmanov.core.remote.providers.TheMovieDatabaseProviderImpl
import com.abdulmanov.core.storage.RoomMoviesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule {

    @Singleton
    @Provides
    fun provideTheMovieDatabaseProvider():TheMovieDatabaseProvider = TheMovieDatabaseProviderImpl()

    @Singleton
    @Provides
    fun provideRoomMoviesDatabase(context: Context):RoomMoviesDatabase = RoomMoviesDatabase.buildDataSource(context)
}