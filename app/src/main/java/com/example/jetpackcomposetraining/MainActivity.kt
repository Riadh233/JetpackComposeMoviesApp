package com.example.jetpackcomposetraining

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetpackcomposetraining.navigation.Screen
import com.example.jetpackcomposetraining.ui.screens.AllMoviesScreen
import com.example.jetpackcomposetraining.ui.screens.DetailsScreen
import com.example.jetpackcomposetraining.ui.screens.MoviesScreen
import com.example.jetpackcomposetraining.ui.theme.JetpackComposeTrainingTheme
import com.example.jetpackcomposetraining.ui.viewmodels.MovieDetailsViewModel
import com.example.jetpackcomposetraining.ui.viewmodels.MoviesViewModel
import com.example.jetpackcomposetraining.ui.viewmodels.SearchMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackComposeTrainingTheme {
                val dispatcher = LocalOnBackPressedDispatcherOwner.current
                    ?.onBackPressedDispatcher
                val callback = remember {
                    object : OnBackPressedCallback(true) {
                        override fun handleOnBackPressed() {
                            Log.d("back pressed","pressed")
                        }
                    }
                }
                dispatcher?.addCallback(callback)
                callback.isEnabled = true
                MoviesApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoviesApp() {
    val searchMoviesViewModel = hiltViewModel<SearchMoviesViewModel>()
    val moviesViewModel = hiltViewModel<MoviesViewModel>()
    val detailsViewModel = hiltViewModel<MovieDetailsViewModel>()
    val navController = rememberNavController()
    val items = listOf(Screen.MoviesScreen, Screen.AllMoviesScreen)
    val showBottomNavBar = mutableStateOf(true)
    val snackBarHostState = remember{ SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            modifier = Modifier,
            snackbarHost = { CustomSnackBarHost(snackBarHostState = snackBarHostState) },
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

        ) {innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.MoviesScreen.route,
                modifier = Modifier.padding(innerPadding),

            ) {
                composable(Screen.MoviesScreen.route) {
                    MoviesScreen(
                        moviesViewModel = moviesViewModel,
                        navController = navController,
                        onShowSnackBar = { message ->
                            coroutineScope.launch {
                                Log.d("tmdb api","show snack bar")
                                snackBarHostState.showSnackbar(message)
                            }
                        }
                    )
                    showBottomNavBar.value = true
                }
                composable(
                    route = Screen.DetailsScreen.route + "/{itemId}",
                    arguments = listOf(
                        navArgument(name = "itemId") {
                            type = NavType.LongType
                        }
                    )) {
                    val itemId = it.arguments?.getLong("itemId")
                    if(itemId != null){
                        detailsViewModel.getMovieById(itemId)
                        DetailsScreen(detailsViewModel.movie,navController)
                    }
                    showBottomNavBar.value = false
                }

                composable(Screen.AllMoviesScreen.route) {
                    AllMoviesScreen(
                        navController = navController,
                        searchResults = searchMoviesViewModel.searchResults.collectAsLazyPagingItems(),
                        searchText = searchMoviesViewModel.searchQuery,
                        updateSearchQuery = searchMoviesViewModel::updateSearchQuery
                    )
                    showBottomNavBar.value = true
                }
            }
        }
    }
}

@Composable
fun CustomSnackBarHost(
    snackBarHostState: SnackbarHostState,
) {
    SnackbarHost(
        hostState = snackBarHostState,
        modifier = Modifier.padding(8.dp),
        snackbar = {data ->
            Snackbar(
                modifier = Modifier.padding(8.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = data.message,
                        tint = Color.Red
                    )
                    Text(text = data.message, color = Color.White)
                }
            }
        }
    )
}
@Composable
fun BackButtonHandler(
    navController: NavController
) {
    val context = LocalContext.current

    val backCallbackHandler = rememberUpdatedState(
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("back pressed","pressed")
                navController.navigateUp()
            }
        }
    )

    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    DisposableEffect(context) {
        dispatcher?.addCallback(backCallbackHandler.value)

        onDispose {
            backCallbackHandler.value.remove()
        }
    }
}


