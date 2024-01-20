package com.example.jetpackcomposetraining.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.jetpackcomposetraining.data.model.Movie
import com.example.jetpackcomposetraining.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    moviesRepository: MoviesRepository
) : ViewModel() {

    val popularMovies = moviesRepository.getPopularMovies().cachedIn(viewModelScope)
    var selectedGenre by mutableStateOf("All")
        private set

    private val selectedGenreFlow = snapshotFlow { selectedGenre }

    @OptIn(ExperimentalCoroutinesApi::class)
    val allMoviesFlow: Flow<PagingData<Movie>> =
        selectedGenreFlow.flatMapLatest { genre ->
            if(genre == "All"){
                moviesRepository.getDiscoverMovies()
            }else{
                moviesRepository.getMoviesWithSelectedGenre(genre)
            }
        }.distinctUntilChanged().cachedIn(viewModelScope)

    fun onSelectedGenreChanged(genre: String) {
        selectedGenre = genre
    }
}