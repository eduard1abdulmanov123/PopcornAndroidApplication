package com.abdulmanov.core.storage.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.abdulmanov.core.storage.contracts.RoomContract
import com.abdulmanov.core.storage.converters.ListConverter

@Entity(tableName = RoomContract.TABLE_MOVIES)
@TypeConverters(ListConverter::class)
class MovieEntity(
    @PrimaryKey
    val id:Long,
    val title:String,
    val overview:String?,
    val releaseDate:String?,
    val genres:List<String>,
    val posterPath:String?,
    val voteCount:Long,
    val voteAverage:Double
)