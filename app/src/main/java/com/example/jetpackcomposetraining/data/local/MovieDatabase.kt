package com.example.jetpackcomposetraining.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jetpackcomposetraining.data.local.converters.CastTypeConverter
import com.example.jetpackcomposetraining.data.local.converters.CrewTypeConverter
import com.example.jetpackcomposetraining.data.local.converters.GenreListConverter
import com.example.jetpackcomposetraining.data.local.dao.MovieDao
import com.example.jetpackcomposetraining.data.local.dao.RemoteKeysDao

@Database(entities = [MovieEntity::class,RemoteKeys::class],version = 5, exportSchema = false)
@TypeConverters(CastTypeConverter::class,CrewTypeConverter::class,GenreListConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun remoteKeysDao() : RemoteKeysDao
}