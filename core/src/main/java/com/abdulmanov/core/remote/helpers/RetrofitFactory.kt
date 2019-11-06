package com.abdulmanov.core.remote.helpers

import android.os.Build
import android.util.Log
import com.abdulmanov.core.remote.helpers.Constants.Companion.BASE_URL
import com.abdulmanov.core.remote.services.TheMovieDatabaseService
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class RetrofitFactory {

    companion object{

        private fun OkHttpClient.Builder.enableTls12() = apply {
            if (Build.VERSION.SDK_INT in 16 until 22) {
                try {
                    val trustManagerFactory =
                        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                    trustManagerFactory.init(null as KeyStore?)
                    val trustManager =
                        trustManagerFactory.trustManagers.first { it is X509TrustManager } as X509TrustManager
                    val sslContext = SSLContext.getInstance(TlsVersion.TLS_1_2.javaName())
                    sslContext.init(null, arrayOf(trustManager), null)
                    sslSocketFactory(Tls12SocketFactory(sslContext.socketFactory), trustManager)
                    val specs = ArrayList<ConnectionSpec>()
                    specs.add(ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_2).build())
                    specs.add(ConnectionSpec.COMPATIBLE_TLS)
                    specs.add(ConnectionSpec.CLEARTEXT)
                    connectionSpecs(specs)
                } catch (e: Exception) {
                    Log.e("HttpClient", "Error while setting TLS 1.2 compatibility")
                }
            }
        }

        private fun getOkHttpInstance(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC

            return OkHttpClient.Builder()
                .enableTls12()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()
        }


        private fun getRetrofitInstance(url:String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(url)
                .client(getOkHttpInstance())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getTheMovieDatabaseService(): TheMovieDatabaseService = getRetrofitInstance(BASE_URL).create(TheMovieDatabaseService::class.java)
    }

}