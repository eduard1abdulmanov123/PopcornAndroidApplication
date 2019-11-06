package com.abdulmanov.MoviCorn.di.module

import android.content.Context

import com.abdulmanov.MoviCorn.di.scope.ActivityScope
import com.abdulmanov.MoviCorn.ui.details_person.DetailsPersonContract
import com.abdulmanov.MoviCorn.ui.details_person.DetailsPersonPresenter
import com.abdulmanov.MoviCorn.ui.details_movie.DetailsMovieContract
import com.abdulmanov.MoviCorn.ui.details_movie.DetailsMoviePresenter
import com.abdulmanov.MoviCorn.ui.library.library_action.LibraryActionContract
import com.abdulmanov.MoviCorn.ui.library.library_action.LibraryActionPresenter
import com.abdulmanov.domain.repositories.MoviesDbRepository
import com.abdulmanov.domain.repositories.MoviesRepository
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val context: Context) {

    /*MovieMedium details*/
    @ActivityScope
    @Provides
    fun provideDetailsMoviePresenter(
        network: MoviesRepository,
        database: MoviesDbRepository
    ): DetailsMovieContract.Presenter {
        return DetailsMoviePresenter(network, database)
    }

    /*Person details*/
    @ActivityScope
    @Provides
    fun provideDetailsPeoplePresenter(
        model: MoviesRepository
    ): DetailsPersonContract.Presenter {
        return DetailsPersonPresenter(model)
    }

    @ActivityScope
    @Provides
    fun provideLibraryActionPresenter(database: MoviesDbRepository):LibraryActionContract.Presenter{
        return LibraryActionPresenter(database)
    }
}