package com.example.jetpackcomposetraining.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.jetpackcomposetraining.data.local.MovieDatabase
import com.example.jetpackcomposetraining.data.network.MovieApi
import com.example.jetpackcomposetraining.data.repository.MoviesRepository
import com.example.jetpackcomposetraining.data.repository.MoviesRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
   moviesRepository : MoviesRepository
) : ViewModel() {
    val allMovies = moviesRepository.getDiscoverMovies(53).cachedIn(viewModelScope)
}