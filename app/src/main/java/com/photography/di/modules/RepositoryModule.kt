package com.photography.di.modules

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
}