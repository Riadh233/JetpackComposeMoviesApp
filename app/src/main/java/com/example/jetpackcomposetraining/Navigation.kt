package com.example.jetpackcomposetraining

import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screen(val route : String){
    object MoviesScreen : Screen("movies_screen")
    object DetailsScreen : Screen("details_screen")
    object AllMoviesScreen : Screen("all_movies_screen")
}