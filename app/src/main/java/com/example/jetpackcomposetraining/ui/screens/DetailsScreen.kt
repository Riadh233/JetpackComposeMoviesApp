package com.example.jetpackcomposetraining.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpackcomposetraining.data.model.Movie
import com.example.jetpackcomposetraining.ui.components.RatingBar
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
                .fillMaxSize()
                .padding(bottom = 3.dp),
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
                        .wrapContentHeight()
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
                        textAlign = TextAlign.Center,
                        lineHeight = 40.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            RatingBar(modifier = Modifier.size(24.dp), movie = movie)
            Spacer(modifier = Modifier.height(16.dp))
            MovieSummary(movie)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Stars",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)){
                items(movie.cast.size){index ->
                    val castMember = movie.cast[index]
                    CreditsItem(name = castMember.name ?: "", imageUrl = castMember.profileImage ?: "")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Crew",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)){
                items(movie.crew.size){index ->
                    val crewMember = movie.crew[index]
                    CreditsItem(name = crewMember.name ?: "", imageUrl = crewMember .profileImage ?: "")
                }
            }
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
        fontSize = 16.sp,
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
}
@Composable
fun CreditsItem(name : String,imageUrl : String){
   Column(horizontalAlignment = Alignment.CenterHorizontally) {
       AsyncImage(
           model = ImageRequest.Builder(LocalContext.current).data(Constants.MOVIE_IMAGE_PATH + imageUrl)
               .crossfade(true)
               .build(),
           contentDescription = name,
           modifier = Modifier
               .size(65.dp, 65.dp)
               .border(width = 1.dp, color = Color.Red, shape = CircleShape)
               .clip(CircleShape),
           contentScale = ContentScale.FillBounds
       )
       val parts = name.split(" ", limit = 2)
       val firstName = if (parts.isNotEmpty()) parts[0] else ""
       val lastName = if (parts.size > 1) parts[1] else ""
       Column(horizontalAlignment = Alignment.CenterHorizontally) {
           Text(text = firstName)
           if (lastName.isNotEmpty()) {
               Text(text = lastName)
           }
       }
   }
}

