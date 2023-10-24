package com.example.jetpackcomposetraining

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.jetpackcomposetraining.navigation.Screen
import com.example.jetpackcomposetraining.screens.AllMoviesScreen
import com.example.jetpackcomposetraining.screens.DetailsScreen
import com.example.jetpackcomposetraining.screens.MoviesScreen
import com.example.jetpackcomposetraining.ui.theme.JetpackComposeTrainingTheme
import com.example.jetpackcomposetraining.ui.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTrainingTheme {
                MoviesApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoviesApp() {
    val viewModel = viewModel<MainViewModel>()
    val navController = rememberNavController()
    val items = listOf(Screen.MoviesScreen, Screen.AllMoviesScreen)
    val showBottomNavBar = mutableStateOf(true)
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            modifier = Modifier,
            bottomBar = {
                if (showBottomNavBar.value) {
                    Log.d(
                        "bottom bar",
                        "current destination : ${navController.currentDestination?.route}"
                    )
                    BottomNavigation(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)),
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                        elevation = 8.dp
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        items.forEach { screen ->
                            val selected =
                                currentDestination?.hierarchy?.any { it.route == screen.route } == true
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        if (screen == Screen.MoviesScreen) Icons.Filled.Home else Icons.Filled.Search,
                                        contentDescription = null,
                                        tint = if (selected) Color.Red else Color.White
                                    )
                                },
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
            }

        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.MoviesScreen.route,
                modifier = Modifier
            ) {
                composable(Screen.MoviesScreen.route) {
                    MoviesScreen(viewModel = viewModel, navController = navController)
                    showBottomNavBar.value = true
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
                    showBottomNavBar.value = false
                }
                composable(Screen.AllMoviesScreen.route) {
                    AllMoviesScreen(navController, viewModel)
                    showBottomNavBar.value = true
                }
            }
        }
    }
}


