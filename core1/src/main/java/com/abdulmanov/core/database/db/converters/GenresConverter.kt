package com.abdulmanov.core.database.db.converters

import androidx.room.TypeConverter

class GenresConverter {

    @TypeConverter
    fun fromGenres(genres:List<String>):String{
        return genres.joinToString(",")
    }

    @TypeConverter
    fun toGenres(data:String):List<String>{
        return data.split(",")
    }
}