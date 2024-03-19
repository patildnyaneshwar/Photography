package com.photography.di.modules

import com.photography.data.local.data_source.PhotosLocalDataSource
import com.photography.data.local.data_source.PhotosLocalDataSourceImpl
import com.photography.data.remote.data_source.PhotosRemoteDataSource
import com.photography.data.remote.data_source.PhotosRemoteDataSourceImpl
import com.photography.data.repository.PhotosRepository
import com.photography.data.repository.PhotosRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providePhotosRepository(photosRepositoryImpl: PhotosRepositoryImpl): PhotosRepository

    @Binds
    abstract fun providePhotosLocalDataSource(photosLocalDataSourceImpl: PhotosLocalDataSourceImpl): PhotosLocalDataSource

    @Binds
    abstract fun providePhotosRemoteDataSource(photosRemoteDataSourceImpl: PhotosRemoteDataSourceImpl): PhotosRemoteDataSource
}