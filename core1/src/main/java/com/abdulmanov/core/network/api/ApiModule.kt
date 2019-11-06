package com.abdulmanov.core.network.api

import android.content.Context
import com.abdulmanov.core.common.enableTls12
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiModule {
    companion object {

        fun getApiMoviesInterface(context: Context, url: String): ApiMoviesInterface {
            return createBuilder(context,url).build().create(ApiMoviesInterface::class.java)
        }

        private fun createBuilder(context: Context, url:String):Retrofit.Builder{
            val client = OkHttpClient.Builder()
                .enableTls12()
                .build()
            return Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
        }
    }
}
