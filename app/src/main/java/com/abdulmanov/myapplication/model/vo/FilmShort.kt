package com.abdulmanov.myapplication.model.vo

import android.os.Parcelable
import com.abdulmanov.customviews.recyclerview.ItemView
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmShort(
    val id: Long,
    val voteAverage: String,
    val voteCount: String,
    val title: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String,
    val genres: String
):ItemView,Parcelable