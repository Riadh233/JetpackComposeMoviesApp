package com.example.jetpackcomposetraining.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.jetpackcomposetraining.BuildConfig
import com.example.jetpackcomposetraining.data.local.MovieDatabase
import com.example.jetpackcomposetraining.data.local.MovieEntity
import com.example.jetpackcomposetraining.data.local.RemoteKeys
import com.example.jetpackcomposetraining.data.network.MovieApi
import com.example.jetpackcomposetraining.data.network.toMovieEntity
import com.example.jetpackcomposetraining.util.Constants
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PopularMoviesRemoteMediator(
    private val moviesApi : MovieApi,
    private val moviesDatabase : MovieDatabase
) : RemoteMediator<Int,MovieEntity>() {

    private val moviesDao = moviesDatabase.movieDao()
    private val remoteKeysDao = moviesDatabase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        Log.d("tmdb remote mediator","triggered load method")
        return try {
            val currentPage = when(loadType){
                LoadType.REFRESH -> {
                    1
            }
                LoadType.PREPEND -> {
                     return MediatorResult.Success(
                            endOfPaginationReached = false
                        )
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            val response = moviesApi.getPopularMovies(page = currentPage, perPage = Constants.ITEMS_PER_PAGE, apiKey = BuildConfig.API_KEY)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            moviesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesDao.deleteAllMovies()
                    remoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.results.map { movie ->
                    RemoteKeys(
                        id = movie.id,
                        prevPage = prevPage,
                        nextPage = nextPage,
                    )
                }
                val moviesList = response.results.map {movieDto ->
                    movieDto.toMovieEntity()
                }
                moviesList.forEach{it.isPopular = true}
                remoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                moviesDao.addMovies(movies = moviesList )
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e : Exception){
            MediatorResult.Error(e)
        }
    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                remoteKeysDao.getRemoteKeys(id = movie.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                remoteKeysDao.getRemoteKeys(id = movie.id)
            }
    }

}