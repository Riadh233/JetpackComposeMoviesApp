package com.example.jetpackcomposetraining.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.jetpackcomposetraining.data.model.Movie
import com.example.jetpackcomposetraining.ui.components.MovieItem
import com.example.jetpackcomposetraining.ui.components.MoviesList
import com.example.jetpackcomposetraining.ui.viewmodels.MoviesViewModel
import com.example.jetpackcomposetraining.ui.viewmodels.SearchMoviesViewModel
import com.example.jetpackcomposetraining.util.Constants
import com.example.jetpackcomposetraining.util.isAppendLoading


 @Composable
fun TestScreen(
    modifier: Modifier = Modifier,
    moviesViewModel: MoviesViewModel,
    searchMoviesViewModel: SearchMoviesViewModel,
    navController: NavController
) {

    LazyColumn(
        modifier = modifier
            .padding(8.dp)
    ) {
        item {
            val discoverMovies = moviesViewModel.allMovies.collectAsLazyPagingItems()
            Spacer(modifier = modifier.height(16.dp))
                MoviesList(
                    modifier = Modifier.size(height = 180.dp, width = 130.dp),
                    moviesList = discoverMovies,
                    navController = navController
                )
        }
        item{
            val popularMovies = moviesViewModel.popularMovies.collectAsLazyPagingItems()
            Spacer(modifier = modifier.height(20.dp))
            Text(
                text = "Popular Movies",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
            )
            Spacer(modifier = modifier.height(8.dp))
            Box(Modifier.fillMaxWidth().wrapContentHeight()) {
                MoviesList(
                    modifier = Modifier.size(height = 200.dp, width = 150.dp),
                    moviesList = popularMovies,
                    navController = navController
                )
            }
        }
    }
}
@Composable
fun PopularMoviesList(modifier: Modifier = Modifier, moviesList: LazyPagingItems<Movie>, navController: NavController){
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), state = rememberLazyListState()){
        items(
            count = moviesList.itemCount,
            key = moviesList.itemKey { it },
            contentType = moviesList.itemContentType { Constants.ITEMS_CONTENT_TYPE }
        ) { index ->
            val movie = moviesList[index]
            if(movie != null)
                MovieItem(modifier = modifier, movie = movie, navController = navController)

        }
        item{
            if(moviesList.isAppendLoading()){
                CircularProgressIndicator()
            }
        }
    }
}