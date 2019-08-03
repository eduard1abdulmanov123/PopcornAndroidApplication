package com.abdulmanov.core.database.di.component


import com.abdulmanov.core.common.ThreadModule
import com.abdulmanov.core.database.di.module.DatabaseModule
import com.abdulmanov.core.database.model.DatabaseModelImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, ThreadModule::class])
interface DatabaseComponent {
    fun inject(databaseModelImpl: DatabaseModelImpl)
}