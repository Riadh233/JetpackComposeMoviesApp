package com.example.jetpackcomposetraining.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposetraining.ui.viewmodels.MainViewModel
import com.example.jetpackcomposetraining.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier,viewModel : MainViewModel){
    val searchText by viewModel.searchText.collectAsState()
    val isTrailingIconVisible = searchText.isNotBlank()
    OutlinedTextField(
        value = searchText,
        onValueChange = viewModel::onSearchTextChanged,
        modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp)),
        placeholder = { Text(text = "Search Movies", color = Color.LightGray) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search),
                tint = Color.LightGray
            )
        },
        trailingIcon = {
            if (isTrailingIconVisible)
                Icon(
                    modifier = Modifier.clickable { viewModel.onSearchTextChanged("") },
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.clear_search_text_icon),
                    tint = Color.LightGray
                )
        },
        shape = RoundedCornerShape(10.dp),
    )

}
