package com.example.jetpackcomposetraining.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposetraining.data.model.Movie

@Composable
fun RatingBar(modifier: Modifier, movie: Movie) {
    Row(
        modifier = Modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until 5) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Star",
                tint = if ((customRound(movie.rating / 2)) > i) Color.Red else Color.White,
                modifier = modifier
            )
        }
        Spacer(modifier = Modifier.width(3.dp))
        Text(text = "(${movie.rating})", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground)
    }
}
fun customRound(number: Float): Int {
    val integerPart = number.toInt()
    val decimalPart = number - integerPart

    return if (decimalPart < 0.5) {
        kotlin.math.floor(number).toInt()
    } else {
        kotlin.math.ceil(number).toInt()
    }
}
