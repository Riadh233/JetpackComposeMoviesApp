package com.example.jetpackcomposetraining.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcomposetraining.ui.viewmodels.MainViewModel
import com.example.jetpackcomposetraining.components.MovieGrid
import com.example.jetpackcomposetraining.components.SearchBar

@Composable
fun AllMoviesScreen(navController : NavController , viewModel : MainViewModel){
    val isSearching by viewModel.isSearching.collectAsState()
    val moviesList by viewModel.movies.collectAsState()
    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Spacer(modifier = Modifier.height(4.dp))
        SearchBar(viewModel = viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        if (isSearching) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        } else
            MovieGrid(moviesList = moviesList,navController = navController)
    }
}