package com.abdulmanov.core.network.common

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import com.abdulmanov.core.common.Constant
import com.abdulmanov.core.network.dto.movies.CastDTO
import com.abdulmanov.core.network.dto.movies.CrewDTO
import com.abdulmanov.core.network.dto.movies.FilmDTO
import com.abdulmanov.core.network.dto.movies.GenresDTO
import com.abdulmanov.core.network.dto.youtube.YouTubeVideosDTO
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import okhttp3.*
import java.security.KeyStore
import javax.net.ssl.X509TrustManager
import okhttp3.TlsVersion
import okhttp3.ConnectionSpec
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory


/**
 * Включает принудительно Tsl12 для версий 16 <= apiMovies < 22
 */
fun OkHttpClient.Builder.enableTls12() = apply {
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

/**
 * Установить кэш размером size.Если соединение с интернетом есть,
 * то закешировать запрос на время maxAge, если интернет осутсвует,
 * то найти кешированный запрос, который не старше чем maxScale
 */
fun OkHttpClient.Builder.installCache(context:Context,size:Long,maxAge:Long,maxScale:Long)=apply {
    cache(Cache(context.cacheDir, size))
    addInterceptor { chain ->
        with(chain.request()) {
            val value =
                if (context.isNetworkAvailable()) "public, max-age=$maxAge" else "public, only-if-cached, max-stale=$maxScale"
            Log.d("HttpClient", value)
            chain.proceed(newBuilder().header("Cache-Control", value).build())
        }
    }
}

/**
 * Проверка на подключение к интернету
 */
fun Context.isNetworkAvailable(): Boolean {
    val connectivityMgr = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityMgr.activeNetworkInfo != null
}


/*Функции - расширения для DTO */
fun GenresDTO.toMap():Map<Long,String>{
    return genres.map { it.id to it.name }.toMap()
}

fun FilmDTO.init(imageUrl:String,genresMap: Map<Long, String>):FilmDTO{
    return copy(
        genres = genres.map {genresMap.getValue(it.toLong())},
        posterPath = imageUrl + posterPath
    )
}

fun CastDTO.getProfilePath():String?{
    return if (profilePath != null) Constant.Network.BASE_PROFILE_PATH_URL_185 + profilePath else null
}

fun CrewDTO.getProfilePath():String?{
    return if (profilePath != null) Constant.Network.BASE_PROFILE_PATH_URL_185 + profilePath else null
}

fun YouTubeVideosDTO.getThumbnail():String?{
    return if(items.isEmpty()) null else items[0].snippet.thumbnails.high.url
}