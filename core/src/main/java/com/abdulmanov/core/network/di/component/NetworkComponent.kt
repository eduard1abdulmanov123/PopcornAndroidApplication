package com.abdulmanov.core.network.di.component


import com.abdulmanov.core.common.ThreadModule
import com.abdulmanov.core.network.di.module.NetworkModule
import com.abdulmanov.core.network.model.NetworkModelImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ThreadModule::class])
interface NetworkComponent {
    fun inject(networkModel: NetworkModelImpl)
}