package com.abdulmanov.MoviCorn.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Filter(
    val sort:String,
    val genres:List<Triple<Int,String,Boolean>>,
    val voteAverageGte: Double,
    val voteAverageLte: Double,
    val releaseDateGte: String,
    val releaseDateLte: String
):Parcelable