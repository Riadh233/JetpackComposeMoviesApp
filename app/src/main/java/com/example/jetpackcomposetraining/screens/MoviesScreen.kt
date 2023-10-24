package com.example.jetpackcomposetraining.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetpackcomposetraining.ui.viewmodels.MainViewModel
import com.example.jetpackcomposetraining.R
import com.example.jetpackcomposetraining.components.ChipsList
import com.example.jetpackcomposetraining.components.ImageHeader
import com.example.jetpackcomposetraining.components.MoviesList
import com.example.jetpackcomposetraining.components.TopBar

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavController
) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .padding(8.dp)
        .verticalScroll(scrollState)) {
        TopBar(imageResource = R.drawable.profile_img)
        Spacer(modifier = modifier.height(16.dp))
        ImageHeader(
            imageList = listOf(
                R.drawable.the_dark_knight,
                R.drawable.inception,
                R.drawable.guy_ritchie_s_the_covenant
            )
        )
        Spacer(modifier = modifier.height(16.dp))
        ChipsList(viewModel = viewModel)
        Spacer(modifier = modifier.height(16.dp))
        MoviesList(
            modifier = Modifier.size(height = 180.dp, width = 130.dp),
            moviesList = viewModel.movies,
            navController = navController
        )
        Spacer(modifier = modifier.height(20.dp))
        Text(
            text = "Popular Movies",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier

        )
        Spacer(modifier = modifier.height(8.dp))
        MoviesList(
            modifier = Modifier.size(height = 200.dp, width = 150.dp),
            moviesList = viewModel.popularMovies,
            navController = navController
        )

    }
}