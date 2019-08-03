package com.abdulmanov.core.database.di.module



import android.content.Context
import com.abdulmanov.core.database.db.MoviesDataBase
import com.abdulmanov.core.database.db.MoviesDataBase.Companion.getDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideDatabase(): MoviesDataBase {
        return getDatabase(context)
    }

}