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
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class SearchMoviesRemoteMediator(
    private val searchText : String,
    private val moviesApi : MovieApi,
    private val moviesDatabase : MovieDatabase
) : RemoteMediator<Int, MovieEntity>() {

    private val moviesDao = moviesDatabase.movieDao()
    private val remoteKeysDao = moviesDatabase.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        Log.d("tmdb api","load fun called")
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeysClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeysForFirstItem(state)
                val prevKey = remoteKeys?.prevPage ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeysForLastItem(state)
                val endOfPagination = remoteKeys != null && remoteKeys.nextPage == null

                val nextKey = remoteKeys?.nextPage ?: return MediatorResult.Success(
                    endOfPaginationReached = endOfPagination
                )
                nextKey
            }
        }
        return try {
            val response = if (searchText.isBlank()) {
                moviesApi.getLatestMovies(
                    page = page,
                    perPage = ITEMS_PER_PAGE,
                    apiKey = BuildConfig.API_KEY
                )
            } else {
                moviesApi.searchMovies(
                    page = page,
                    perPage = ITEMS_PER_PAGE,
                    query = searchText,
                    apiKey = BuildConfig.API_KEY
                )
            }
            val moviesResponse = response.results
            val moviesWithCredits = moviesResponse.map {
                moviesApi.getMovieWithCredits(it.id,BuildConfig.API_KEY)
            }

            val endOfPaginationReached = moviesResponse.isEmpty()
            Log.d("tmdb api","page $page, search text $searchText item count ${moviesResponse.size}")
            moviesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesDao.deleteLatestMovies()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val movies = moviesWithCredits.map { movie ->
                    val castList = movie.credits.cast.filter { castMember ->
                        castMember.profileImage != null
                    }.distinctBy { it.name }
                    val crewList = movie.credits.crew.filter { crewMember ->
                        crewMember.profileImage != null
                    }.distinctBy { it.name }
                    delay(1)
                    movie.toMovieEntity(System.currentTimeMillis(),castList,crewList, listType = 2)
                }
                val keys = moviesResponse.map { (id) -> RemoteKeys(id = id , prevPage = prevKey, nextPage = nextKey) }

                moviesDao.addMovies(movies)
                remoteKeysDao.addAllRemoteKeys(keys)
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }

        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }
    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { (id) ->
                remoteKeysDao.getRemoteKeys(id)
            }
    }
    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { (id) -> remoteKeysDao.getRemoteKeys(id) }
    }

    private suspend fun getRemoteKeysClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { bookId ->
                remoteKeysDao.getRemoteKeys(bookId)
            }
        }
    }
}