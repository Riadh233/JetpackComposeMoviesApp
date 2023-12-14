package com.example.jetpackcomposetraining.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.example.jetpackcomposetraining.data.model.Movie
import com.example.jetpackcomposetraining.ui.viewmodels.MainViewModel
import com.example.jetpackcomposetraining.ui.components.MovieGrid
import com.example.jetpackcomposetraining.ui.components.SearchBar
import com.example.jetpackcomposetraining.util.isRefreshSuccess

@Composable
fun AllMoviesScreen(navController : NavController , viewModel : MainViewModel,moviesList: LazyPagingItems<Movie>){
    val isSearching by viewModel.isSearching.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        Spacer(modifier = Modifier.height(4.dp))
        SearchBar(viewModel = viewModel)
        Spacer(modifier = Modifier.height(16.dp))
        MovieGrid(moviesList = moviesList,navController = navController)
//        if (isSearching) {
//            Box(modifier = Modifier.fillMaxSize()) {
//                CircularProgressIndicator(Modifier.align(Alignment.Center))
//            }
//        } else
//            MovieGrid(moviesList = moviesList,navController = navController)

    }
}