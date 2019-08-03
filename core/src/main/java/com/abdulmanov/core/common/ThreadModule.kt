package com.abdulmanov.core.common


import com.abdulmanov.core.common.Constant.Thread.Companion.IO_THREAD
import com.abdulmanov.core.common.Constant.Thread.Companion.UI_THREAD
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class ThreadModule {

    @Provides
    @Singleton
    @Named(UI_THREAD)
    fun provideSchedulersUI(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @Singleton
    @Named(IO_THREAD)
    fun provideSchedulersIO(): Scheduler {
        return Schedulers.io()
    }
}