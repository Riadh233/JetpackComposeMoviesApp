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
import com.example.jetpackcomposetraining.util.Constants.ITEMS_PER_PAGE
import retrofit2.HttpException
import java.io.IOException

 @OptIn(ExperimentalPagingApi::class)
 class DiscoverMoviesRemoteMediator (
    private val moviesApi : MovieApi,
    private val moviesDatabase : MovieDatabase,
) : RemoteMediator<Int,MovieEntity>() {
     private val moviesDao = moviesDatabase.movieDao()
     private val remoteKeysDao = moviesDatabase.remoteKeysDao()

     override suspend fun load(
         loadType: LoadType,
         state: PagingState<Int, MovieEntity>
     ): MediatorResult {

         val currentPage = when(loadType){
             LoadType.REFRESH -> {
                 val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                 remoteKeys?.nextPage?.minus(1) ?: 1
             }
             LoadType.PREPEND -> {
                 val remoteKeys = getRemoteKeyForFirstItem(state)
                 val prevPage = remoteKeys?.prevPage
                     ?: return MediatorResult.Success(
                         endOfPaginationReached = remoteKeys != null
                     )
                 prevPage
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
         return try {
             val response = moviesApi.getAllMovies(page = currentPage, perPage = ITEMS_PER_PAGE, apiKey = BuildConfig.API_KEY)
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
                         nextPage = nextPage
                     )
                 }
                 remoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                 moviesDao.addMovies(movies = response.results.map { it.toMovieEntity() })
             }
             MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

         } catch (e : IOException){
             MediatorResult.Error(e)
         } catch (e : HttpException){
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