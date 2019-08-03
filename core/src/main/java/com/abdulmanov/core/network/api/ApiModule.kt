package com.abdulmanov.core.network.api

import android.content.Context
import com.abdulmanov.core.common.Constant.Network.Companion.CACHE_SIZE
import com.abdulmanov.core.common.Constant.Network.Companion.MAX_AGE
import com.abdulmanov.core.common.Constant.Network.Companion.MAX_SCALE
import com.abdulmanov.core.network.common.enableTls12
import com.abdulmanov.core.network.common.installCache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiModule {
    companion object {

        fun getApiMoviesInterface(context: Context, url: String): ApiMoviesInterface {
            return createBuilder(context,url).build().create(ApiMoviesInterface::class.java)
        }

        fun getApiTrailerInterface(context: Context,url:String): ApiTrailerInterface{
            return createBuilder(context,url).build().create(ApiTrailerInterface::class.java)
        }

        private fun createBuilder(context: Context, url:String):Retrofit.Builder{
            val client = OkHttpClient.Builder()
                .enableTls12()
                //.installCache(context, CACHE_SIZE.toLong(), MAX_AGE.toLong(), MAX_SCALE.toLong())
                .build()
            return Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
        }
    }
}
