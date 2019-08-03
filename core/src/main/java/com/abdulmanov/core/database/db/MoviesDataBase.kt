package com.abdulmanov.core.database.db



import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.abdulmanov.core.common.Constant.Database.Companion.DATABASE_NAME
import com.abdulmanov.core.common.Constant.Database.Companion.DATABASE_VERSION
import com.abdulmanov.core.database.db.dao.MovieDao
import com.abdulmanov.core.database.db.entity.Movie


@Database(entities = [Movie::class],version = DATABASE_VERSION)
abstract class MoviesDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        fun getDatabase(context: Context):MoviesDataBase{
            return Room.databaseBuilder(context, MoviesDataBase::class.java, DATABASE_NAME).build()
        }
    }
}