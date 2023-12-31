package com.example.jetpackcomposetraining.data.repository

import androidx.paging.PagingData
import com.example.jetpackcomposetraining.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
     fun getDiscoverMovies(genre : Int) : Flow<PagingData<Movie>>
     fun getPopularMovies() : Flow<PagingData<Movie>>
     fun searchMovies(searchText : String) : Flow<PagingData<Movie>>
     suspend fun getMovieById(id:Long) : Movie
}