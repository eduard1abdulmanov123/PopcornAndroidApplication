package com.abdulmanov.core.database.db.entity


import androidx.room.*
import com.abdulmanov.core.database.db.converters.GenresConverter

@Entity(tableName = "movies")
@TypeConverters(GenresConverter::class)
data class Movie(
    @ColumnInfo(name = "id_movie")
    val idMovie:Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "genres")
    val genres:List<String>,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "vote_count")
    val voteCount: Long,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int=0
)