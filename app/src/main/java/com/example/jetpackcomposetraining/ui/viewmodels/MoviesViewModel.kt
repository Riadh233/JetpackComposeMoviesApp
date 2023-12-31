package com.example.jetpackcomposetraining.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.jetpackcomposetraining.data.model.FakeMovie
import com.example.jetpackcomposetraining.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
   moviesRepository : MoviesRepository
) : ViewModel() {
    val allMovies = moviesRepository.getDiscoverMovies(53).cachedIn(viewModelScope)
    val popularMovies = moviesRepository.getPopularMovies().cachedIn(viewModelScope)


}