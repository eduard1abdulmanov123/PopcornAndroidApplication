package com.abdulmanov.core.network.dto.youtube

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class YouTubeVideosDTO(
    @SerializedName("items")
    @Expose
    val items:List<ItemVideoDTO>
)

data class ItemVideoDTO(
    @SerializedName("snippet")
    @Expose
    val snippet: SnippetDTO
)

data class SnippetDTO(
    @SerializedName("thumbnails")
    @Expose
    val thumbnails:ThumbnailsDTO
)

data class ThumbnailsDTO(
    @SerializedName("default")
    @Expose
    val default:QualityDTO,
    @SerializedName("medium")
    @Expose
    val medium:QualityDTO,
    @SerializedName("high")
    @Expose
    val high:QualityDTO,
    @SerializedName("standard")
    @Expose
    val standard:QualityDTO,
    @SerializedName( "maxres")
    @Expose
    val maxres:QualityDTO
)

data class QualityDTO(
    @SerializedName("url")
    @Expose
    val url:String,
    @SerializedName("width")
    @Expose
    val width:Int,
    @SerializedName("height")
    @Expose
    val height:Int
)