package com.example.jetpackcomposetraining.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.jetpackcomposetraining.data.local.MovieDatabase
import com.example.jetpackcomposetraining.data.local.MovieEntity
import com.example.jetpackcomposetraining.data.local.toDomainModel
import com.example.jetpackcomposetraining.data.model.Movie
import com.example.jetpackcomposetraining.data.network.MovieApi
import com.example.jetpackcomposetraining.data.paging.DiscoverMoviesRemoteMediator
import com.example.jetpackcomposetraining.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi : MovieApi,
    private val moviesDatabase : MovieDatabase,
    private val dispatcherIO : CoroutineDispatcher
) : MoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getDiscoverMovies(genre : Int): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { moviesDatabase.movieDao().getDiscoverMovies() }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = DiscoverMoviesRemoteMediator(
                moviesApi = moviesApi,
                moviesDatabase = moviesDatabase,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData -> pagingData.map(MovieEntity::toDomainModel) }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        TODO()
//        val pagingSourceFactory = { moviesDatabase.movieDao().getPopularMovies() }
//
//        return Pager(
//            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
//            remoteMediator = PopularMoviesRemoteMediator(
//                moviesApi = moviesApi,
//                moviesDatabase = moviesDatabase
//            ),
//            pagingSourceFactory = pagingSourceFactory
//        ).flow.map { pagingData -> pagingData.map(MovieEntity::toDomainModel) }
//            .flowOn(dispatcherIO)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun searchMovies(searchText : String): Flow<PagingData<Movie>> {
        TODO()
//        val pagingSourceFactory = { moviesDatabase.movieDao().searchMovie(searchText) }
//
//        return Pager(
//            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
//            remoteMediator = SearchMoviesRemoteMediator(
//                moviesApi = moviesApi,
//                moviesDatabase = moviesDatabase,
//                searchText = searchText
//            ),
//            pagingSourceFactory = pagingSourceFactory
//        ).flow.map { pagingData -> pagingData.map(MovieEntity::toDomainModel) }
//            .flowOn(dispatcherIO)
    }

    override fun getMovieById(id: Long): Movie {
        return moviesDatabase.movieDao().getMovieById(id).toDomainModel()
    }
}