package com.example.jetpackcomposetraining.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposetraining.ui.viewmodels.MainViewModel
import com.example.jetpackcomposetraining.util.getAllMovieGenres

@Composable
fun ChipsList(viewModel: MainViewModel) {
    val moviesGenres = getAllMovieGenres()
    val selectedGenre = viewModel.selectedGenre.value
    LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        items(moviesGenres) { genre ->
            val isSelected = genre == selectedGenre
            GenreChip(genre, isSelected , viewModel::onSelectedGenreChanged)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GenreChip(genre : String,
              isSelected : Boolean,
              onSelectedGenreChanged : (String) -> Unit,
              ){
    val transition =
        updateTransition(targetState = isSelected, label = "Category Selection Transition")

    val backgroundColor by transition.animateColor(label = "Background Color Animation") { isCurrentlySelected ->
        if (isCurrentlySelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor by transition.animateColor(label = "Text Color Animation") { isCurrentlySelected ->
        if (isCurrentlySelected) MaterialTheme.colorScheme.onSurface else Color.LightGray
    }
    Chip(
        onClick = {
            onSelectedGenreChanged(genre) },
        shape = RoundedCornerShape(8.dp),
        colors = ChipDefaults.chipColors(backgroundColor = backgroundColor),
    ){
        Text(
            text = genre,
            color = textColor,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp)
        )
    }
}
@Preview
@Composable
fun ChipOverview(){

}