package com.abdulmanov.MoviCorn.model

import android.os.Parcelable
import com.abdulmanov.customviews.recyclerview.ItemView
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Long,
    val voteAverage: String,
    val voteCount: String,
    val title: String,
    val posterPath: String?,
    val overview: String,
    val releaseDate: String?,
    val genres: List<String>
):ItemView,Parcelable