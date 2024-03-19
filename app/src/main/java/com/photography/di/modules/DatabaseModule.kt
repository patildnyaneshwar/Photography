package com.photography.di.modules

import android.app.Application
import androidx.room.Room
import com.photography.data.local.database.PhotosDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ) = Room.databaseBuilder(application, PhotosDatabase::class.java, "photos_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesPhotoDao(db: PhotosDatabase) = db.photosDao()

}