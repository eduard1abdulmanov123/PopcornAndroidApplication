package com.abdulmanov.core.network.api

import com.abdulmanov.core.common.Constant.Network.Companion.KEY_TRAILER
import com.abdulmanov.core.network.dto.youtube.YouTubeVideosDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiTrailerInterface {
    @GET("videos?part=snippet&key=$KEY_TRAILER")
    fun getTrailerInfo(@Query("id") id:String): Call<YouTubeVideosDTO>
}