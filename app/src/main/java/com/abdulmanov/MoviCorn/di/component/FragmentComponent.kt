package com.abdulmanov.MoviCorn.di.component

import com.abdulmanov.MoviCorn.di.module.FragmentModule
import com.abdulmanov.MoviCorn.di.scope.FragmentScope
import com.abdulmanov.MoviCorn.ui.library.LibraryFragment
import com.abdulmanov.MoviCorn.ui.movie.MovieFragment
import com.abdulmanov.MoviCorn.ui.search.SearchFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(movieFragment:MovieFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(library:LibraryFragment)
}