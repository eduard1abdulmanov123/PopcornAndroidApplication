package com.abdulmanov.core.storage.converters

import androidx.room.TypeConverter

class ListConverter {

    @TypeConverter
    fun fromList(list:List<String>):String{
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(data:String):List<String>{
        return data.split(",")
    }
}