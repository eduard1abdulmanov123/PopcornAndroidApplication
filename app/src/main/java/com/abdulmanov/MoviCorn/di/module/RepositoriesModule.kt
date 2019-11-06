package com.abdulmanov.MoviCorn.di.module

import com.abdulmanov.core.remote.providers.TheMovieDatabaseProvider
import com.abdulmanov.core.remote.providers.TheMovieDatabaseProviderImpl
import com.abdulmanov.core.storage.RoomMoviesDatabase
import com.abdulmanov.domain.converters.*
import com.abdulmanov.domain.repositories.MoviesDbRepository
import com.abdulmanov.domain.repositories.MoviesDbRepositoryImpl
import com.abdulmanov.domain.repositories.MoviesRepository
import com.abdulmanov.domain.repositories.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(
        theMovieDatabaseProvider: TheMovieDatabaseProvider,
        roomMoviesDatabase: RoomMoviesDatabase,
        movieDetailsConverter: MovieDetailsConverter,
        moviesConverter: MoviesConverter,
        peopleConverter: PeopleConverter,
        genresConverter: GenresConverter

    ): MoviesRepository {
        return MoviesRepositoryImpl(
            theMovieDatabaseProvider,
            roomMoviesDatabase,
            movieDetailsConverter,
            moviesConverter,
            peopleConverter,
            genresConverter
        )
    }

    @Singleton
    @Provides
    fun provideMoviesDbRepository(
       roomMoviesDatabase: RoomMoviesDatabase,
       moviesDbConverter: MoviesDbConverter
    ):MoviesDbRepository{
        return MoviesDbRepositoryImpl(roomMoviesDatabase,moviesDbConverter)
    }
}