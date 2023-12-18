package com.example.jetpackcomposetraining.util

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.jetpackcomposetraining.data.model.Movie

fun LazyPagingItems<Movie>.isRefreshSuccess() = loadState.source.refresh is LoadState.NotLoading
        || loadState.mediator?.refresh is LoadState.NotLoading

fun LazyPagingItems<Movie>.isRefreshEmpty() = loadState.source.refresh is LoadState.NotLoading
        && loadState.mediator?.refresh is LoadState.NotLoading
        && itemCount == 0

fun LazyPagingItems<Movie>.isRefreshLoading() =
    loadState.mediator?.refresh == LoadState.Loading && itemCount == 0

fun LazyPagingItems<Movie>.isRefreshError() = loadState.mediator?.refresh is LoadState.Error
        && itemCount == 0

fun LazyPagingItems<Movie>.isAppendLoading() = (loadState.mediator?.append is LoadState.Loading)
        || (loadState.mediator?.refresh == LoadState.Loading && itemCount > 0)

fun LazyPagingItems<Movie>.isAppendError() = (loadState.mediator?.append is LoadState.Error)
        || (loadState.mediator?.refresh is LoadState.Error && itemCount > 0)

fun LazyPagingItems<Movie>.appendError(): LoadState.Error =
    (loadState.mediator?.append as? LoadState.Error)
        ?: (loadState.mediator?.refresh as LoadState.Error)

fun LazyPagingItems<Movie>.refreshError(): LoadState.Error = (loadState.refresh as LoadState.Error)