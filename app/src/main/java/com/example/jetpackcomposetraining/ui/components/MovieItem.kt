package com.example.jetpackcomposetraining.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpackcomposetraining.data.model.Movie
import com.example.jetpackcomposetraining.navigation.Screen
import com.example.jetpackcomposetraining.util.Constants.ITEMS_CONTENT_TYPE
import com.example.jetpackcomposetraining.util.Constants.MOVIE_IMAGE_PATH
import com.example.jetpackcomposetraining.util.isAppendError
import com.example.jetpackcomposetraining.util.isAppendLoading
import com.example.jetpackcomposetraining.util.isRefreshError
import com.example.jetpackcomposetraining.util.isRefreshLoading
import com.example.jetpackcomposetraining.util.isRefreshSuccess
import com.example.jetpackcomposetraining.util.refreshError
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


@SuppressLint("SuspiciousIndentation")
@Composable
fun MovieGrid(moviesList: LazyPagingItems<Movie>, navController: NavController){

    Box(modifier = Modifier.fillMaxSize()){
        if (moviesList.isRefreshError()) {
            ErrorLayout(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(6.dp),
                onRetryClicked = { moviesList.refresh() }, error = moviesList.refreshError()?.error
            )
        }else{
            if(moviesList.isRefreshSuccess()){
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
                    item (span = { GridItemSpan(maxLineSpan) }){
                        if (moviesList.isAppendLoading() || moviesList.isAppendError()) {
                            IsAppendLayout(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center),
                                isLoading = moviesList.isAppendLoading(),
                                isError = moviesList.isAppendError(),
                                onRetryClicked = { moviesList.retry() }
                            )
                        }
                    }
                }
            }
            if(moviesList.isRefreshLoading()){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}


@Composable
fun MoviesList(
    modifier: Modifier = Modifier,
    moviesList: LazyPagingItems<Movie>,
    navController: NavController,
) {
    if(moviesList.isAppendError()){
        ErrorLayout(
            modifier = Modifier
                .padding(6.dp),
            onRetryClicked = { moviesList.refresh() }, error = moviesList.refreshError()?.error )
    }else{
        if(moviesList.isRefreshSuccess()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                state = rememberLazyListState(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(
                    count = moviesList.itemCount,
                    key = moviesList.itemKey { it },
                    contentType = moviesList.itemContentType { ITEMS_CONTENT_TYPE }
                ) { index ->
                    val movie = moviesList[index]
                    if (movie != null)
                        MovieItem(modifier = modifier, movie = movie, navController = navController)

                }
                item {
                    if (moviesList.isAppendLoading() || moviesList.isAppendError()) {
                        IsAppendLayout(
                            modifier = Modifier
                                .fillMaxHeight()
                            ,
                            isLoading = moviesList.isAppendLoading(),
                            isError = moviesList.isAppendError(),
                            onRetryClicked = {moviesList.retry()}
                        )
                    }
                }
            }
        }
        if (moviesList.isRefreshLoading()) {
            CircularProgressIndicator(modifier = Modifier.fillMaxHeight())
        }
    }
}

@Composable
fun IsAppendLayout(modifier : Modifier = Modifier,
                   isLoading : Boolean,
                   isError : Boolean,
                   onRetryClicked: () -> Unit
) {
    Box(
        modifier = modifier,
    ) {
        if (isLoading) CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 12.dp)
                .size(24.dp),
            strokeWidth = 2.dp,
            )

        if (isError) {
            IconButton(modifier = Modifier.align(Alignment.Center), onClick = onRetryClicked) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh"
                )
            }
        }
    }
}

@Composable
fun ErrorLayout(
    modifier: Modifier = Modifier,
    onRetryClicked: () -> Unit,
    error: Throwable?
){
    val message = when(error){
        is SocketTimeoutException, is UnknownHostException -> "Internet Unavailable"
        is HttpException -> "Server Error"
        else -> "Connection Problem"
    }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(modifier = modifier,text = message, textAlign = TextAlign.Center)
        IconButton(
            onClick = onRetryClicked,
            modifier = modifier.size(20.dp,20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Retry",
                tint = Color.White
            )
        }
    }
}

@Composable
fun MovieItem(modifier: Modifier = Modifier, movie: Movie,
              navController: NavController) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier
            .clickable {
                navController.navigate(Screen.DetailsScreen.route + "/${movie.id}")
            }
            .fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(MOVIE_IMAGE_PATH + movie.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = movie.title,
                modifier = Modifier
                    .align(Alignment.Center)
                    ,
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
