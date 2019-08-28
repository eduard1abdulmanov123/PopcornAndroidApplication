package com.abdulmanov.MoviCorn.model.vo.movie

import android.os.Parcelable
import com.abdulmanov.customviews.recyclerview.ItemView
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmMedium(
    val id: Long,
    val voteAverage: String,
    val voteCount: String,
    val title: String,
    val posterPath: String?,
    val overview: String,
    val releaseDate: String?,
    val genres: String
):ItemView,Parcelable