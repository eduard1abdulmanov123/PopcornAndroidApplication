package com.abdulmanov.MoviCorn.di.module

import com.abdulmanov.domain.converters.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ConvertersModule {

    @Singleton
    @Provides
    fun provideGenresConverter():GenresConverter = GenresConverterImpl()

    @Singleton
    @Provides
    fun provideMoviesConverter(genresConverter: GenresConverter):MoviesConverter{
        return MoviesConverterImpl(genresConverter)
    }

    @Singleton
    @Provides
    fun provideMovieDetailsConverter(moviesConverter: MoviesConverter):MovieDetailsConverter{
        return MovieDetailsConverterImpl(moviesConverter)
    }

    @Singleton
    @Provides
    fun providePeopleConverter():PeopleConverter = PeopleConverterImpl()

    @Singleton
    @Provides
    fun provideMoviesDbConverter():MoviesDbConverter = MoviesDbConverterImpl()
}