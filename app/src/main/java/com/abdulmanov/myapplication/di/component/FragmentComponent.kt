package com.abdulmanov.myapplication.di.component

import com.abdulmanov.myapplication.di.module.FragmentModule
import com.abdulmanov.myapplication.di.scope.FragmentScope
import com.abdulmanov.myapplication.ui.library.LibraryFragment
import com.abdulmanov.myapplication.ui.movie.MovieFragment
import com.abdulmanov.myapplication.ui.search.SearchFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(movieFragment:MovieFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(library:LibraryFragment)
}