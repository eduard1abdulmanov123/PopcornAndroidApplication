package com.abdulmanov.myapplication.di.component

import com.abdulmanov.myapplication.di.module.ActivityModule
import com.abdulmanov.myapplication.di.scope.ActivityScope
import com.abdulmanov.myapplication.ui.details.DetailsActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(detailsActivity: DetailsActivity)
}