package com.abdulmanov.myapplication.di.module

import com.abdulmanov.myapplication.di.scope.FragmentScope
import com.abdulmanov.myapplication.model.vo.FilmLibrary
import com.abdulmanov.myapplication.model.vo.FilmShort
import com.abdulmanov.myapplication.ui.common_ui.BaseMovieContract
import com.abdulmanov.myapplication.ui.common_ui.BaseMoviePresenter
import com.abdulmanov.myapplication.ui.library.LibraryContract
import com.abdulmanov.myapplication.ui.library.LibraryPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule{

    @FragmentScope
    @Provides
    fun provideMoviePresenter():BaseMovieContract.Presenter<FilmShort> = BaseMoviePresenter()

    @FragmentScope
    @Provides
    fun provideMovieLibraryPresenter():LibraryContract.Presenter<FilmLibrary> = LibraryPresenter()
}