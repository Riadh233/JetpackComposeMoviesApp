package com.example.jetpackcomposetraining.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jetpackcomposetraining.data.local.converters.CreditsConverter
import com.example.jetpackcomposetraining.data.local.converters.GenreListConverter
import com.example.jetpackcomposetraining.data.local.dao.MovieDao
import com.example.jetpackcomposetraining.data.local.dao.RemoteKeysDao

@Database(entities = [MovieEntity::class,RemoteKeys::class],version = 1, exportSchema = false)
@TypeConverters(CreditsConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun remoteKeysDao() : RemoteKeysDao
}