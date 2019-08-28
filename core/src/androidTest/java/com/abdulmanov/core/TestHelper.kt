package com.abdulmanov.core

import com.abdulmanov.core.database.db.entity.Movie

class TestHelper {
    companion object{

        fun createMovies(count:Int):List<Movie>{
            val list = mutableListOf<Movie>()
            for(i in 0 until count){
                list.add(
                    Movie(
                        (i*123).toLong(),
                        i.toString(),
                        i.toString(),
                        i.toString(),
                        listOf("Приключение", "Фантастика", "Боевик"),
                        "Постер",
                        (i*2314).toLong(),
                        124.23,
                        i+1
                    )
                )
            }
            return list
        }
    }
}