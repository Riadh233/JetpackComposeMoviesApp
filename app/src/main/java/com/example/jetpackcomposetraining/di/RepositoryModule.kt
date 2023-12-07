package com.example.jetpackcomposetraining.di

import com.example.jetpackcomposetraining.data.repository.MoviesRepository
import com.example.jetpackcomposetraining.data.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMyRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository
}