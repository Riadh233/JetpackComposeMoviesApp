package com.example.jetpackcomposetraining.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.jetpackcomposetraining.MainViewModel
import com.example.jetpackcomposetraining.Movie
import com.example.jetpackcomposetraining.R
import com.example.jetpackcomposetraining.components.ImageHeader
import com.example.jetpackcomposetraining.components.MovieGrid
import com.example.jetpackcomposetraining.components.MoviesList
import com.example.jetpackcomposetraining.components.SearchBar
import com.example.jetpackcomposetraining.components.TopBar

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavController
) {
    Column(modifier = Modifier.padding(8.dp)) {
        TopBar(imageResource = R.drawable.subreddit_placeholder)
        Spacer(modifier = modifier.height(16.dp))
        ImageHeader(
            imageList = listOf(
                R.drawable.thailand,
                R.drawable.thailand,
                R.drawable.thailand
            )
        )
        Spacer(modifier = modifier.height(16.dp))
        MoviesList(viewModel = viewModel, navController = navController)
    }
}