package com.example.jetpackcomposetraining.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcomposetraining.ui.viewmodels.MainViewModel
import com.example.jetpackcomposetraining.data.Movie
import com.example.jetpackcomposetraining.R
import com.example.jetpackcomposetraining.navigation.Screen
import kotlinx.coroutines.flow.StateFlow


@Composable
fun MovieGrid(viewModel: MainViewModel, navController: NavController){
    val moviesList by viewModel.movies.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(moviesList) { movie ->
            MovieItem(modifier = Modifier.size(height = 235.dp, width = 160.dp), movie = movie, navController = navController)
        }
    }

}
@Composable
fun MoviesList(modifier: Modifier = Modifier, moviesList: StateFlow<List<Movie>>, navController: NavController){
    val movieList by moviesList.collectAsState()
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)){
        items(movieList){movie ->
            MovieItem(modifier = modifier, movie = movie, navController = navController)
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
            Image(
                painter = painterResource(movie.imgUrl),
                contentDescription = stringResource(id = R.string.header_image),
                modifier = Modifier
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
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