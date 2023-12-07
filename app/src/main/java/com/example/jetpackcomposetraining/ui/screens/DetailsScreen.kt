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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposetraining.data.model.FakeMovie
import com.example.jetpackcomposetraining.R
import com.example.jetpackcomposetraining.ui.components.RatingBar
import com.webtoonscorp.android.readmore.foundation.ReadMoreTextOverflow
import com.webtoonscorp.android.readmore.foundation.ToggleArea
import com.webtoonscorp.android.readmore.material3.ReadMoreText

@Composable
fun DetailsScreen(currentMovie: FakeMovie) {
    val scrollState = rememberScrollState()
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
            Image(
                painter = painterResource(currentMovie.imgUrl),
                contentDescription = stringResource(id = R.string.header_image),
                modifier = Modifier
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
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
                    text = currentMovie.title,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Bottom),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        RatingBar(modifier = Modifier.size(24.dp), movie = currentMovie)
        Spacer(modifier = Modifier.height(16.dp))
        MovieSummary(currentMovie)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(14.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Watch Movie",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                    )

            }
        }
    }
}

@Composable
fun MovieSummary(currentMovie: FakeMovie) {
    val (expanded, onExpandedChange) = rememberSaveable { mutableStateOf(false) }
    Text(
        text = "Summary",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
    )
    Spacer(modifier = Modifier.height(16.dp))

    ReadMoreText(
        text = currentMovie.description,
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
        text = "Director: ${currentMovie.director}",
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
        text = "${currentMovie.actors}",
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onBackground,
    )
}

@Preview
@Composable
fun DetailsScreenPreview(){
    DetailsScreen(
        FakeMovie(
            3,
            "Oppenheimer",
           "short descrip tionxzccc cccccccccc ccc cccccccccccccc csddddddddddd ddddddddddddddd" +
                   "short descrip tionxzccc cccccccccc ccc cccccccccccccc csddddddddddd ddddddddddddddd" +
                   "short descrip tionxzccc cccccccccc ccc cccccccccccccc csddddddddddd ddddddddddddddd" +
                   "short descrip tionxzccc cccccccccc ccc cccccccccccccc csddddddddddd ddddddddddddddd",
            R.drawable.oppenheimer,
            4.5f,
            "1.5M",
            "Cristopher Nolan",
            listOf("Cylian Murphy","Cylian Murphy","Cylian Murphy")
        )
    )
}