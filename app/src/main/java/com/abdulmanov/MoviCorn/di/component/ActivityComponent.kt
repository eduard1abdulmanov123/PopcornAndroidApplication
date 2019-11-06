package com.abdulmanov.MoviCorn.di.component

import com.abdulmanov.MoviCorn.di.module.ActivityModule
import com.abdulmanov.MoviCorn.di.scope.ActivityScope
import com.abdulmanov.MoviCorn.ui.details_person.DetailsPersonActivity
import com.abdulmanov.MoviCorn.ui.details_movie.DetailsMovieActivity
import com.abdulmanov.MoviCorn.ui.library.library_action.LibraryActionActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(detailsMovieActivity: DetailsMovieActivity)

    fun inject(detailsPersonActivity: DetailsPersonActivity)

    fun inject(libraryActionActivity: LibraryActionActivity)
}