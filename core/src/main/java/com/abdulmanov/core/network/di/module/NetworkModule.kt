package com.abdulmanov.core.network.di.module


import android.content.Context
import com.abdulmanov.core.common.Constant.Network.Companion.MOVIES_BASE_URL
import com.abdulmanov.core.common.Constant.Network.Companion.YOUTUBE_BASE_URL
import com.abdulmanov.core.network.api.ApiMoviesInterface
import com.abdulmanov.core.network.api.ApiModule
import com.abdulmanov.core.network.api.ApiTrailerInterface
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideApiMoviesInterface(@Named(MOVIES_BASE_URL) base_url: String): ApiMoviesInterface {
        return ApiModule.getApiMoviesInterface(context, base_url)
    }

    @Provides
    @Singleton
    fun provideApiTrailerInterface(@Named(YOUTUBE_BASE_URL) base_url: String):ApiTrailerInterface{
        return ApiModule.getApiTrailerInterface(context,base_url)
    }

    @Provides
    @Singleton
    @Named(YOUTUBE_BASE_URL)
    fun provideYoutubeBaseUrl():String{
        return YOUTUBE_BASE_URL
    }

    @Provides
    @Singleton
    @Named(MOVIES_BASE_URL)
    fun provideMoviesBaseUrl(): String {
        return MOVIES_BASE_URL
    }
}