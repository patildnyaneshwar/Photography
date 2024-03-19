package com.photography.data.local.data_source

import com.photography.ui.presentation.home.model.PhotosModel
import kotlinx.coroutines.flow.Flow

interface PhotosLocalDataSource {

    fun getAllPhotos(): Flow<List<PhotosModel>>

    suspend fun deleteAllPhotos()

    suspend fun insertAllPhotos(data: List<PhotosModel>)

}