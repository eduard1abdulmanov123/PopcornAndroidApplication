package com.abdulmanov.MoviCorn.di.module

import com.abdulmanov.MoviCorn.di.scope.FragmentScope
import com.abdulmanov.MoviCorn.model.vo.FilmLibrary
import com.abdulmanov.MoviCorn.model.vo.movie.FilmMedium
import com.abdulmanov.MoviCorn.ui.common_ui.BaseListMovieContract
import com.abdulmanov.MoviCorn.ui.common_ui.BaseMoviePresenter
import com.abdulmanov.MoviCorn.ui.library.LibraryContract
import com.abdulmanov.MoviCorn.ui.library.LibraryPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule{

    @FragmentScope
    @Provides
    fun provideMoviePresenter():BaseListMovieContract.Presenter<FilmMedium> = BaseMoviePresenter()

    @FragmentScope
    @Provides
    fun provideMovieLibraryPresenter():LibraryContract.Presenter<FilmLibrary> = LibraryPresenter()
}