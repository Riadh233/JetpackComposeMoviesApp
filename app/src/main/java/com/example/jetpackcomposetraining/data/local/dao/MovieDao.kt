package com.example.jetpackcomposetraining.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.jetpackcomposetraining.data.local.MovieEntity


@Dao
interface MovieDao {
    @Upsert
    fun addMovies(movies : List<MovieEntity>)

    @Query("SELECT * FROM movies_table WHERE isPopular = 0")
    fun getDiscoverMovies() : PagingSource<Int,MovieEntity>

    @Query("SELECT * FROM movies_table WHERE isPopular = 1")
    fun getPopularMovies() : PagingSource<Int,MovieEntity>

    @Query("SELECT * FROM movies_table WHERE id = :id")
    fun getMovieById(id : Long) : MovieEntity

    @Query("SELECT * FROM movies_table WHERE title = :query")
    fun searchMovie(query : String) : PagingSource<Int,MovieEntity>

    @Query("DELETE FROM movies_table")
    fun deleteAllMovies()
}