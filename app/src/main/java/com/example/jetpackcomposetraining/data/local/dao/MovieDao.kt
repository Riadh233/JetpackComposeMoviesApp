package com.example.jetpackcomposetraining.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.jetpackcomposetraining.data.local.MovieEntity


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(movies : List<MovieEntity>)

    @Query("SELECT * FROM movies_table WHERE listType = 0 AND genreList LIKE '%' || :genre || '%' ORDER BY timestamp ASC")
    fun getMoviesWithGenre(genre : String) : PagingSource<Int,MovieEntity>

    @Query("SELECT * FROM movies_table WHERE listType = 0 ORDER BY timestamp ASC")
    fun getDiscoverMovies() : PagingSource<Int,MovieEntity>

    @Query("SELECT * FROM movies_table WHERE listType = 1 ORDER BY timestamp ASC")
    fun getPopularMovies() : PagingSource<Int,MovieEntity>
    @Query("SELECT * FROM movies_table WHERE listType = 2 ORDER BY timestamp ASC")
    fun getLatestMovies() : PagingSource<Int,MovieEntity>

    @Query("SELECT * FROM movies_table WHERE id = :id")
    fun getMovieById(id : Long) : MovieEntity

    @Query("SELECT * FROM movies_table WHERE title LIKE '%' || :query || '%' ORDER BY timestamp ASC")
    fun searchMovie(query : String) : PagingSource<Int,MovieEntity>

    @Query("DELETE FROM movies_table WHERE listType = 0")
    fun deleteDiscoverMovies()

    @Query("DELETE FROM movies_table WHERE listType = 1")
    fun deletePopularMovies()

    @Query("DELETE FROM movies_table WHERE listType = 2")
    fun deleteLatestMovies()
}