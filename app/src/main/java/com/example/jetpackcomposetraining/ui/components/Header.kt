package com.example.jetpackcomposetraining.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposetraining.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageHeader(modifier: Modifier = Modifier,imageList: List<Int>){
    val pagerState = rememberPagerState(pageCount = {
        imageList.size
    })
    Column {
        HorizontalPager(state = pagerState) { image ->
            Box(modifier = modifier
                .fillMaxWidth()
                .height(180.dp)){
                MovieImage(image = imageList[image])
            }
        }
        Spacer(modifier = modifier.height(3.dp))
        ImageIndicator(selectedIndex = pagerState.currentPage,imageList.size)
        Text(
            text = stringResource(id = R.string.find_your),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(id = R.string.fav_movie),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )

    }
}

@Composable
fun MovieImage(image: Int,modifier: Modifier = Modifier) {
    Image(
        ImageBitmap.imageResource(id = image),
        contentDescription = stringResource(id = R.string.header_image),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
    )
}
@Composable
fun ImageIndicator(selectedIndex: Int,imageCount : Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
        ) {
        for (i in 0 until imageCount) {
            Dot(
                isSelected = i == selectedIndex,
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}

@Composable
fun Dot(isSelected: Boolean, modifier: Modifier) {
    val color = if(isSelected) MaterialTheme.colorScheme.onSurface else Color.Gray
    Box(modifier = modifier
        .size(7.dp)
        .background(color = color, shape = CircleShape))
}

@Composable
@Preview
fun ImageHeaderPreview() {
    ImageHeader(imageList = listOf(R.drawable.the_dark_knight, R.drawable.inception, R.drawable.guy_ritchie_s_the_covenant))
}
