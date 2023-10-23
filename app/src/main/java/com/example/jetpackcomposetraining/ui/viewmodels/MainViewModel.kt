package com.example.jetpackcomposetraining.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposetraining.data.FakeApi
import com.example.jetpackcomposetraining.data.Movie
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val fakeApi = FakeApi(application.applicationContext)

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

   private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val moviesList = fakeApi.getMovies(searchText.value)
    private val getPopularMovies = fakeApi.getMovies("popular")

    private val _movies = MutableStateFlow(moviesList)
    val movies = searchText
        .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(_movies){text,movies ->
            if(text.isBlank()){
                movies
            }else{
                movies.filter {
                    delay(500L)
                    it.doesMatchSearchQuery(text)
                }
            }
        }.onEach { _isSearching.update { false } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),_movies.value)

    private val _popularMovies = MutableStateFlow(getPopularMovies)
    val popularMovies : StateFlow<List<Movie>>
        get() = _popularMovies

    var selectedGenre  = mutableStateOf("")

    fun onSearchTextChanged(text : String){
        _searchText.value = text
    }

    fun getMovieById(itemId: Int): Movie {
        return moviesList[itemId]
    }
    fun onSelectedGenreChanged(genre : String){
        selectedGenre.value = genre
        _searchText.value = genre
    }
}