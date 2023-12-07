package com.example.jetpackcomposetraining.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetpackcomposetraining.data.model.Movie
import com.example.jetpackcomposetraining.ui.viewmodels.MoviesViewModel


@Composable
fun TestScreen(
    viewModel: MoviesViewModel,
){
    val movies = viewModel.allMovies.collectAsLazyPagingItems()
    Log.d("load state tmdb",movies.loadState.toString())
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally){
        items(
            count = movies.itemCount,
            key = {index -> movies[index]?.id ?: -1 },
            contentType = { 0 }
        ){index ->
            val movie = movies[index]
            if(movie != null){
                TestItem(movie = movie)
            }
        }
    }
}

@Composable
fun TestItem(modifier: Modifier = Modifier, movie: Movie) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp)
    ) {
        Text(
            text = movie.title,
            fontSize = 14.sp
        )
    }

}
