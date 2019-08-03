package com.abdulmanov.core.database.db.entity


import androidx.room.*


@Entity(tableName = "movies")
data class Movie(
    @ColumnInfo(name = "id_movie")
    val idMovie:Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "genres")
    val genres:String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int=0
)