package com.example.jetpackcomposetraining.ui.viewmodels

import android.util.Log
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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchMoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set

    @OptIn(FlowPreview::class)
    val searchQueryFlow = snapshotFlow { searchQuery.trim() }.debounce(500).conflate()

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults: Flow<PagingData<Movie>> =
        searchQueryFlow.flatMapLatest { text ->
            if(text.isBlank()){
                getAllMovies()
            }else{
                val result = searchMovies(text)
                Log.d("search query",text)
                result
            }
            }.distinctUntilChanged().cachedIn(viewModelScope)


    private fun searchMovies(query: String): Flow<PagingData<Movie>> =
        moviesRepository.searchMovies(query)

    private fun getAllMovies() : Flow<PagingData<Movie>> = moviesRepository.getPopularMovies()
    fun updateSearchQuery(query : String){
        searchQuery = query
    }

}
