package com.example.jetpackcomposetraining.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpackcomposetraining.R
import com.example.jetpackcomposetraining.data.model.Movie
import com.example.jetpackcomposetraining.util.Constants
import com.webtoonscorp.android.readmore.foundation.ReadMoreTextOverflow
import com.webtoonscorp.android.readmore.foundation.ToggleArea
import com.webtoonscorp.android.readmore.material3.ReadMoreText
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DetailsScreen(movieFlow: StateFlow<Movie?>) {
    val scrollState = rememberScrollState()
    val movie = movieFlow.collectAsState().value
    if(movie != null){
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(Constants.MOVIE_IMAGE_PATH + movie.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    contentScale = ContentScale.FillBounds
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                0F to Color.Transparent,
                                .5F to Color.Black.copy(alpha = 0.5F),
                                1F to MaterialTheme.colorScheme.background.copy(alpha = 1F)
                            )
                        )
                ) {
                    Text(
                        text = movie.title,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Bottom),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
//        RatingBar(modifier = Modifier.size(24.dp), movie = currentMovie)
            Spacer(modifier = Modifier.height(16.dp))
            MovieSummary(movie)
            Spacer(modifier = Modifier.height(16.dp))

        }

    }
}

@Composable
fun MovieSummary(currentMovie: Movie) {
    val (expanded, onExpandedChange) = rememberSaveable { mutableStateOf(false) }
    Text(
        text = "Summary",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
    )
    Spacer(modifier = Modifier.height(16.dp))

    ReadMoreText(
        text = currentMovie.overview,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 14.sp,
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = Modifier.width(260.dp),
        readMoreText = "Read more",
        readMoreColor = Color.Red,
        readMoreFontSize = 15.sp,
        readMoreMaxLines = 3,
        readMoreOverflow = ReadMoreTextOverflow.Ellipsis,
        readLessText = "Read less",
        readLessColor = Color.Red,
        readLessFontSize = 15.sp,
        toggleArea = ToggleArea.More,
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Director: ${currentMovie.title}",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.colorScheme.onBackground,
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Stars",
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Red,
    )
    Text(
        text = currentMovie.title,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onBackground,
    )
}
@Composable
fun CreditsItem(name : String,imageUrl : String){
   Column {
       AsyncImage(
           model = ImageRequest.Builder(LocalContext.current).data(Constants.MOVIE_IMAGE_PATH + imageUrl)
               .crossfade(true)
               .build(),
           contentDescription = name,
           modifier = Modifier
               .fillMaxSize()
               .clip(CircleShape),
           contentScale = ContentScale.FillBounds
       )
       Text(
           text = name,
           color = MaterialTheme.colorScheme.onSurface,
           )
   }
}

