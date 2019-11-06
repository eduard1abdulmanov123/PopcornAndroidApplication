package com.abdulmanov.core.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdulmanov.core.storage.contracts.RoomContract
import com.abdulmanov.core.storage.dao.MovieDao
import com.abdulmanov.core.storage.models.MovieEntity

@Database(entities = [MovieEntity::class],version = RoomContract.DATABASE_MOVIES_VERSION,exportSchema = false)
abstract class RoomMoviesDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        fun buildDataSource(context: Context): RoomMoviesDatabase = Room.databaseBuilder(
            context, RoomMoviesDatabase::class.java, RoomContract.DATABASE_MOVIES
        ).build()
    }
}