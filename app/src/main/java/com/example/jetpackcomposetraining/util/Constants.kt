package com.example.jetpackcomposetraining.util

object Constants {
    const val DATABASE_NAME = "movies_database"
    const val MOVIES_ENTITY = "movies_table"
    const val REMOTE_KEYS_ENTITY = "remote_keys"
    const val ITEMS_PER_PAGE = 20
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val MOVIE_IMAGE_PATH = "https://image.tmdb.org/t/p/w500"
    const val ITEMS_CONTENT_TYPE = "Movies Items"

    val MOVIE_GENRES = listOf(
        "All",
        "Action",
        "Adventure",
        "Animation",
        "Comedy",
        "Crime",
        "Documentary",
        "Drama",
        "Family",
        "Fantasy",
        "History",
        "Horror",
        "Music",
        "Mystery",
        "Romance",
        "Science Fiction",
        "TV Movie",
        "Thriller",
        "War",
        "Western"
    )
}