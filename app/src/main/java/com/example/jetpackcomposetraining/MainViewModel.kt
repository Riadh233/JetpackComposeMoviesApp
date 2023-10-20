package com.example.jetpackcomposetraining

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val fakeApi = FakeApi(application.applicationContext)
    private val moviesList = fakeApi.getMovies()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

   private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

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

    fun onSearchTextChanged(text : String){
        _searchText.value = text
    }

    fun getMovieById(itemId: Int): Movie {
        return moviesList[itemId]
    }

}