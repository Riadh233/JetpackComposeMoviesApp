package com.example.jetpackcomposetraining.ui.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpackcomposetraining.data.model.FakeMovie
import com.example.jetpackcomposetraining.data.model.Movie
import com.example.jetpackcomposetraining.navigation.Screen
import com.example.jetpackcomposetraining.ui.viewmodels.MoviesViewModel
import com.example.jetpackcomposetraining.util.Constants.ITEMS_CONTENT_TYPE
import com.example.jetpackcomposetraining.util.Constants.MOVIE_IMAGE_PATH
import com.example.jetpackcomposetraining.util.isAppendError
import com.example.jetpackcomposetraining.util.isAppendLoading
import com.example.jetpackcomposetraining.util.isRefreshEmpty


@SuppressLint("SuspiciousIndentation")
@Composable
fun MovieGrid(moviesList: LazyPagingItems<Movie>, navController: NavController){
    Log.d("tmdb api",moviesList.loadState.toString())
    LazyVerticalGrid(
        state = rememberLazyGridState(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = moviesList.itemCount,
            key = moviesList.itemKey { it },
            contentType = moviesList.itemContentType { ITEMS_CONTENT_TYPE }
        ) { index ->
            val movie = moviesList[index]
            if(movie != null)
            MovieItem(modifier = Modifier.size(height = 235.dp, width = 170.dp), movie = movie, navController = navController)

        }
    }
}


@Composable
fun MoviesList(modifier: Modifier = Modifier, moviesList: LazyPagingItems<Movie>, navController: NavController){
    Log.d("tmdb api",moviesList.loadState.toString())
         LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), state = rememberLazyListState()){
             items(
                 count = moviesList.itemCount,
                 key = moviesList.itemKey { it },
                 contentType = moviesList.itemContentType { ITEMS_CONTENT_TYPE }
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

@Composable
fun MovieItem(modifier: Modifier = Modifier, movie: Movie, navController: NavController) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = modifier.clickable{
            navController.navigate(Screen.DetailsScreen.route + "/${movie.id}")
        }) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(MOVIE_IMAGE_PATH + movie.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = movie.title,
                modifier = Modifier
                    .align(Alignment.Center),
                contentScale = ContentScale.FillBounds
            )
            Column(
                Modifier
                    .align(Alignment.BottomStart)
                    .background(
                        Brush.verticalGradient(
                            0F to Color.Transparent,
                            .5F to Color.Black.copy(alpha = 0.5F),
                            1F to Color.Black.copy(alpha = 0.8F)
                        )
                    )
                    .padding(4.dp)
            ) {
                Text(
                    text = movie.title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(5.dp),
                    color = MaterialTheme.colorScheme.onSurface,
                    overflow = TextOverflow.Ellipsis
                )
                RatingBar(Modifier.size(13.dp), movie)
            }
        }
    }
}


@Composable
@Preview
fun MoviesGridPreview(){
}

@Composable
@Preview
fun RatingBarPreview(){
}