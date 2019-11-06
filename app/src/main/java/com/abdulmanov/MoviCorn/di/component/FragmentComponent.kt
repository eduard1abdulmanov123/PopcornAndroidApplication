package com.abdulmanov.MoviCorn.di.component

import com.abdulmanov.MoviCorn.di.module.FragmentModule
import com.abdulmanov.MoviCorn.di.scope.FragmentScope
import com.abdulmanov.MoviCorn.ui.library.library_main.LibraryFragment
import com.abdulmanov.MoviCorn.ui.movie.list.MovieListFragment
import com.abdulmanov.MoviCorn.ui.movie.main.MovieMainFragment
import com.abdulmanov.MoviCorn.ui.search.filter_search.SearchFilterFragment
import com.abdulmanov.MoviCorn.ui.search.string_search.StringSearchFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(library: LibraryFragment)
    fun inject(movieMain:MovieMainFragment)
    fun inject(movieListFragment: MovieListFragment)
    fun inject(stringSearchFragment: StringSearchFragment)
    fun inject(searchFilterFragment: SearchFilterFragment)
}