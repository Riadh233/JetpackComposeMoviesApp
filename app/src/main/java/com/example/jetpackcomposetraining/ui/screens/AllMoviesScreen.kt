package com.example.jetpackcomposetraining.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetpackcomposetraining.data.model.Movie
import com.example.jetpackcomposetraining.ui.components.MovieGrid
import com.example.jetpackcomposetraining.ui.components.SearchBar
import com.example.jetpackcomposetraining.ui.viewmodels.SearchMoviesViewModel

@Composable
fun AllMoviesScreen(navController : NavController , viewModel : SearchMoviesViewModel){
    val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
    Log.d("tmdb api",searchResults.loadState.toString())
    Log.d("tmdb api","list count " + searchResults.itemCount )
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        Spacer(modifier = Modifier.height(4.dp))
        SearchBar(viewModel = viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        MovieGrid(moviesList = searchResults,navController = navController)
    }
}