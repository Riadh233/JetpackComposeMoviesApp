package com.example.jetpackcomposetraining.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposetraining.R

@Composable
fun TopBar(imageResource : Int,modifier: Modifier = Modifier){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            ImageBitmap.imageResource(id = imageResource),
            contentDescription = stringResource(id = R.string.profie_image),
            modifier = modifier
                .size(50.dp, 50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds,
            alignment = Alignment.Center
        )
        Spacer(modifier = modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.good_morning), 
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium, 
                color =Color.Red,
            )   
            Spacer(modifier = modifier.height(3.dp))
            Text(
                text = stringResource(id = R.string.profile_name), 
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold, 
                color =MaterialTheme.colorScheme.onBackground,
            )
        }
        Surface(
            modifier = modifier
                .size(30.dp)
                .padding(3.dp)
                .wrapContentSize(Alignment.Center),
            shape = RoundedCornerShape(6.dp),
            shadowElevation = 4.dp,
            color = Color.DarkGray,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.notifications_icon),
                contentDescription = stringResource(id = R.string.profie_image),
                tint = Color.Gray
            )
        }
    }
}

@Composable
@Preview
fun ProfileImagePreview(){
    TopBar(imageResource = R.drawable.subreddit_placeholder, modifier = Modifier)
}
