package com.example.jetpackcomposetraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcomposetraining.screens.AllMoviesScreen
import com.example.jetpackcomposetraining.screens.DetailsScreen
import com.example.jetpackcomposetraining.screens.MoviesScreen
import com.example.jetpackcomposetraining.ui.theme.JetpackComposeTrainingTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTrainingTheme{
                val viewModel = viewModel<MainViewModel>()
                val navController = rememberNavController()
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    MainScreen(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController,viewModel: MainViewModel){
    val items = listOf(Screen.MoviesScreen,Screen.AllMoviesScreen)
    Scaffold(
        modifier = Modifier
        ,
        bottomBar = {
            BottomNavigation(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp,20.dp,0.dp,0.dp)),
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                elevation = 8.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    BottomNavigationItem(
                        icon = { Icon(
                            if(screen == Screen.MoviesScreen) Icons.Filled.Home else Icons.Filled.Search,
                            contentDescription = null, tint = if(selected) Color.Red else Color.White)},
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        selectedContentColor = Color.Red
                    )
                }
            }
        }

    ) { innerPadding ->
        NavHost(navController = navController, startDestination = Screen.MoviesScreen.route, modifier = Modifier.padding(innerPadding)){
            composable(Screen.MoviesScreen.route){
                MoviesScreen(viewModel = viewModel,navController = navController)
            }
            composable(
                route = Screen.DetailsScreen.route + "/{itemId}",
                arguments = listOf(
                    navArgument(name = "itemId") {
                        type = NavType.IntType
                    }
                )) {
                val itemId = it.arguments?.getInt("itemId")
                DetailsScreen(viewModel.getMovieById(itemId!!))
            }
            composable(Screen.AllMoviesScreen.route){
                AllMoviesScreen(navController, viewModel)
            }
        }
    }
}


