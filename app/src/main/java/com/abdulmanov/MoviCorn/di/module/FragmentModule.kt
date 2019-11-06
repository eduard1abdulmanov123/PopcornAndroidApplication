package com.abdulmanov.MoviCorn.di.module

import com.abdulmanov.MoviCorn.di.scope.FragmentScope
import com.abdulmanov.MoviCorn.ui.library.library_main.LibraryContract
import com.abdulmanov.MoviCorn.ui.library.library_main.LibraryPresenter
import com.abdulmanov.MoviCorn.ui.movie.list.MovieListContract
import com.abdulmanov.MoviCorn.ui.movie.list.MovieListPresenter
import com.abdulmanov.MoviCorn.ui.movie.main.MovieMainContract
import com.abdulmanov.MoviCorn.ui.movie.main.MovieMainPresenter
import com.abdulmanov.MoviCorn.ui.search.filter_search.SearchFilterContract
import com.abdulmanov.MoviCorn.ui.search.filter_search.SearchFilterPresenter
import com.abdulmanov.MoviCorn.ui.search.string_search.StringSearchContract
import com.abdulmanov.MoviCorn.ui.search.string_search.StringSearchPresenter
import com.abdulmanov.domain.repositories.MoviesDbRepository
import com.abdulmanov.domain.repositories.MoviesRepository
import dagger.Module
import dagger.Provides

@Module
class FragmentModule{

    @FragmentScope
    @Provides
    fun provideMovieListPresenter():MovieListContract.Presenter{
        return MovieListPresenter()
    }

    @FragmentScope
    @Provides
    fun provideStringSearchPresenter():StringSearchContract.Presenter{
        return StringSearchPresenter()
    }

    @FragmentScope
    @Provides
    fun provideSearchFilterPresenter(model:MoviesRepository):SearchFilterContract.Presenter{
        return SearchFilterPresenter(model)
    }

    @FragmentScope
    @Provides
    fun provideMovieMainPresenter(model:MoviesRepository):MovieMainContract.Presenter{
        return MovieMainPresenter(model)
    }

    @FragmentScope
    @Provides
    fun provideLibraryPresenter(database:MoviesDbRepository): LibraryContract.Presenter{
        return LibraryPresenter(database)
    }
}