package com.example.jetpackcomposetraining.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposetraining.data.model.Movie
import com.example.jetpackcomposetraining.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var movie by mutableStateOf<Movie?>(null)
        private set

    fun getMovieById(id: Long) {
        viewModelScope.launch {
                val fetchedMovie = moviesRepository.getMovieById(id)
                movie = fetchedMovie
                Log.d("detail screen", fetchedMovie.title)
        }
    }
}